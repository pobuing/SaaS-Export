package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.common.utils.DownloadUtil;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ContractExample;
import com.itheima.saas.domain.vo.ContractProductVo;
import com.itheima.saas.service.cargo.ContractService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/29 21:30
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/cargo/contract")
public class ContractController extends BaseController {
    @Reference
    private ContractService contractService;


    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        example.setOrderByClause("create_time desc");
        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/contract/contract-list";
    }

    @RequestMapping(value = "/toAdd")
    public String toAdd() {
        return "cargo/contract/contract-add";
    }


    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-update";
    }

    @RequestMapping(value = "/edit")
    public String edit(Contract contract) {
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        if (StringUtils.isEmpty(contract.getId())) {
            contract.setId(UUID.randomUUID().toString());
            contract.setCreateTime(new Date());
            //保存数据
            contractService.save(contract);
        } else {
            contract.setCreateTime(new Date());
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }


    @RequestMapping(value = "/print")
    public String print() {
        return "cargo/print/contract-print";
    }

    @RequestMapping(value = "/printExcelByTemplate")
    public void printExcelByTemplate(String inputDate) throws IOException {
        //1、通过inputDate查询出所有要导出excel表的数据
        List<ContractProductVo> vos = contractService.findByInputDate(inputDate, companyId);

        //2、创建工作薄
        Workbook wb = new XSSFWorkbook("D:\\02_lessons\\JAVAEE_118_Class\\day10\\01-Code\\export_parent\\export_web_manager\\src\\main\\webapp\\make\\xlsprint\\tOUTPRODUCT.xlsx");

        //3、读取页
        Sheet sheet = wb.getSheetAt(0);

        //4、处理大标题
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        cell.setCellValue(inputDate.replace("-", "年") + "月份出货表");

        //5、处理小标题

        //6、处理数据
        //6.1、读取数据这一行
        row = sheet.getRow(2);
        //6.2、读取这一行所有单元格的样式
        /*cell = row.getCell(1);
        CellStyle cellStyle1 = cell.getCellStyle();

        cell = row.getCell(2);
        CellStyle cellStyle2 = cell.getCellStyle();

        cell = row.getCell(3);
        CellStyle cellStyle3 = cell.getCellStyle();*/

        CellStyle[] cellStyles = new CellStyle[9];
        for (int i = 1; i < 9; i++) {
            cell = row.getCell(i);
            cellStyles[i] = cell.getCellStyle();
        }

        //6.3、循环数据
        //6.3.1、创建行，创建单元格，写入数据
        int index = 2;
        for (ContractProductVo vo : vos) {
            //7、在循环里，将vo数据写入单元格，创建单元格
            row = sheet.createRow(index);

            cell = row.createCell(1);
            cell.setCellValue(vo.getCustomName());
            cell.setCellStyle(cellStyles[1]);

            cell = row.createCell(2);
            cell.setCellValue(vo.getContractNo());
            cell.setCellStyle(cellStyles[2]);

            cell = row.createCell(3);
            cell.setCellValue(vo.getProductNo());
            cell.setCellStyle(cellStyles[3]);

            cell = row.createCell(4);
            cell.setCellValue(vo.getCnumber());
            cell.setCellStyle(cellStyles[4]);

            cell = row.createCell(5);
            cell.setCellValue(vo.getFactoryName());
            cell.setCellStyle(cellStyles[5]);

            cell = row.createCell(6);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
            cell.setCellStyle(cellStyles[6]);

            cell = row.createCell(7);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
            cell.setCellStyle(cellStyles[7]);

            cell = row.createCell(8);
            cell.setCellValue(vo.getTradeTerms());
            cell.setCellStyle(cellStyles[8]);

            index++;
        }

        //7、导出excel
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        new DownloadUtil().download(outputStream, response, "模板出货表.xlsx");
    }

    @RequestMapping(value = "/printExcel")
    public void printExcel(String inputDate) throws IOException {
        //1、通过inputDate查询出所有要导出excel表的数据
        List<ContractProductVo> vos = contractService.findByInputDate(inputDate, companyId);
        //System.out.println("==============================="+vos.size());

        //2、创建工作薄
        Workbook wb = new SXSSFWorkbook(); //new XSSFWorkbook();
        //3、创建页
        Sheet sheet = wb.createSheet();

        //设置列宽
        sheet.setColumnWidth(1, 26 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 30 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 10 * 256);

        //合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 1, 8);
        sheet.addMergedRegion(cellAddresses);

        //4、创建一个大标题
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(1);

        //设置样式
        row.setHeightInPoints(36);
        /*CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);*/

        cell.setCellStyle(bigTitle(wb));

        //2015-01  --> 2015年01月份出货表      2015年01
        cell.setCellValue(inputDate.replace("-", "年") + "月份出货表");

        //5、创建一个小标题
        row = sheet.createRow(1);

        row.setHeightInPoints(26);

        cell = row.createCell(1);
        cell.setCellValue("客户");
        cell.setCellStyle(title(wb));

        cell = row.createCell(2);
        cell.setCellValue("合同号");
        cell.setCellStyle(title(wb));

        cell = row.createCell(3);
        cell.setCellValue("货号");
        cell.setCellStyle(title(wb));

        cell = row.createCell(4);
        cell.setCellValue("数量");
        cell.setCellStyle(title(wb));

        cell = row.createCell(5);
        cell.setCellValue("工厂");
        cell.setCellStyle(title(wb));

        cell = row.createCell(6);
        cell.setCellValue("工厂交期");
        cell.setCellStyle(title(wb));

        cell = row.createCell(7);
        cell.setCellValue("船期");
        cell.setCellStyle(title(wb));

        cell = row.createCell(8);
        cell.setCellValue("贸易条款");
        cell.setCellStyle(title(wb));

        //6、循环vos，得到每个vo
        int index = 2;
        for (ContractProductVo vo : vos) {

            for (int i = 0; i < 6000; i++) {
                //7、在循环里，将vo数据写入单元格，创建单元格
                row = sheet.createRow(index);

                cell = row.createCell(1);
                cell.setCellValue(vo.getCustomName());
                //cell.setCellStyle(text(wb));

                cell = row.createCell(2);
                cell.setCellValue(vo.getContractNo());
                //cell.setCellStyle(text(wb));

                cell = row.createCell(3);
                cell.setCellValue(vo.getProductNo());
                //cell.setCellStyle(text(wb));

                cell = row.createCell(4);
                cell.setCellValue(vo.getCnumber());
                //cell.setCellStyle(text(wb));

                cell = row.createCell(5);
                cell.setCellValue(vo.getFactoryName());
                //cell.setCellStyle(text(wb));

                cell = row.createCell(6);
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
                //cell.setCellStyle(text(wb));

                cell = row.createCell(7);
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
                //cell.setCellStyle(text(wb));

                cell = row.createCell(8);
                cell.setCellValue(vo.getTradeTerms());
                //cell.setCellStyle(text(wb));

                index++;
            }
        }
        //8、导出excel
        //ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response, String returnName
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        new DownloadUtil().download(outputStream, response, "出货表.xlsx");
    }


    //大标题的样式
    public CellStyle bigTitle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);                //横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);                //横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);        //纵向居中
        style.setBorderTop(BorderStyle.THIN);                        //上细线
        style.setBorderBottom(BorderStyle.THIN);                    //下细线
        style.setBorderLeft(BorderStyle.THIN);                        //左细线
        style.setBorderRight(BorderStyle.THIN);                        //右细线

        return style;
    }


    @RequestMapping(value = "/submit")
    public String submit(String id) {
        Contract contract = contractService.findById(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping(value = "/toView")
    public String toView(String id) {
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "cargo/contract/contract-view";
    }


}
