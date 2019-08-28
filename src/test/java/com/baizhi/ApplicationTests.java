package com.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.BannerService;
import com.baizhi.service.UserService;
import com.baizhi.util.Md5UUIDSaltUtil;
import com.baizhi.vo.UserSexCount;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private BannerDAO bannerDAO;
    @Autowired
    private ArticleDAO articleDAO;
    @Autowired
    private AlbumDAO albumDAO;
    @Autowired
    private GuruDAO guruDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void Test1() {
        Admin admin = new Admin();
        admin.setName("admin");
        Admin one = adminDAO.selectOne(admin);
        System.out.println("one-----------" + one);
    }

    @Test
    public void Test2() {
      /*  int i = adminDAO.insert(new Admin("123456", "admin", "admin"));
        System.out.println(i);*/
        Admin admin = new Admin();
        RowBounds rowBounds = new RowBounds(0, 5);
        List<Admin> admins = adminDAO.selectByRowBounds(admin, rowBounds);
        admins.forEach(a -> System.out.println(a));
    }

    @Test
    public void Test3() {
        Map<String, Object> map = bannerService.showAllByPage(1, 2);
        System.out.println(map.get("page"));
        System.out.println(map.get("rows"));
        System.out.println(map.get("records"));
        System.out.println(map.get("total"));
    }


    @Test
    public void Test5() {
        Banner banner = new Banner();
        banner.setId("1234").setLastUpdateDate(new Date());
        int i = bannerDAO.updateByPrimaryKeySelective(banner);
        System.out.println(i);
    }

    /**
     * 利用pio测试输出Excel表格
     */
    @Test
    public void TestPIOOut() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("province");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Code");
        row.createCell(3).setCellValue("ProvinceId");
        List<City> cities = cityDAO.selectAll();
        for (int i = 0; i < cities.size(); i++) {
            HSSFRow sheetRow = sheet.createRow(i + 1);
            sheetRow.createCell(0).setCellValue(cities.get(i).getId());
            sheetRow.createCell(1).setCellValue(cities.get(i).getName());
            sheetRow.createCell(2).setCellValue(cities.get(i).getCode());
            sheetRow.createCell(3).setCellValue(cities.get(i).getProvinceId());
        }
        try {
            workbook.write(new FileOutputStream(new File("C:/Users/Lenovo/Desktop/111/citys.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用easyPio
     * 测试数据库的信息的导出
     */
    @Test
    public void TestEasyPioOut() {
        List<Album> albums = albumDAO.selectAll();
        for (Album album : albums) {
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album.getId());
            List<Chapter> select = chapterDAO.select(chapter);
            album.setChapters(select);
            album.setCover("D:\\IDEA\\cmfz\\cmfz_ycj\\src\\main\\webapp\\view\\album\\image\\" + album.getCover());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑信息", "Album"), Album.class, albums);
        try {
            workbook.write(new FileOutputStream(new File("C:/Users/Lenovo/Desktop/111/city.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件的导入
     */
    @Test
    public void testPioImport() {
        ImportParams params = new ImportParams();
        try {
            List<City> cities = ExcelImportUtil.importExcel(new FileInputStream(new File("C:/Users/Lenovo/Desktop/111/citys.xls")), City.class, params);
            cities.forEach(city -> System.out.println(city));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testecharts() {
        Map<String, Object> map = userService.selectCountByDays();

    }

    @Test
    public void testCount() {
        List<UserSexCount> sexCounts = userDAO.findBoyGroupByProcince();
        System.out.println(sexCounts);
        List<UserSexCount> byProcince = userDAO.findGrilGroupByProcince();
        System.out.println(byProcince);
    }

    @Test
    public void testGoeasy() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-5ef79b4d44d2467799f085015a79dff8");
        goEasy.publish("my_channel", "1234");

    }

    @Test
    public void testAliDayu() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIMqd7f1NkNVkY", "g6y8t11hzQ9gE1EPrKPEPMXjcnd10L");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15670862253");
        request.putQueryParameter("SignName", "图书购物网站");
        request.putQueryParameter("TemplateCode", "SMS_172742959");
        String salt = Md5UUIDSaltUtil.getSalt();
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + salt + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test6(){
        Admin admin = new Admin();
        String salt = Md5UUIDSaltUtil.getSalt();
        Md5Hash md5Hash = new Md5Hash("123456",salt,1024);
        admin.setId(UUID.randomUUID().toString().replace("-",""))
                .setName("admin0").setRole("svip").setSalt(salt)
                .setPassword(md5Hash.toString());
        int i = adminDAO.insertSelective(admin);
        System.out.println(i);

    }
}
