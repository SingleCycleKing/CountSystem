package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Unique Studio on 15/3/6.
 */
public class ExeclUtil {
    public static ArrayList readExecl(String path, int column, int row) {
        ArrayList<String> mStrings = new ArrayList<>();
        try {
            Workbook mWorkbook = Workbook.getWorkbook(new File(path));
            Sheet mSheet = mWorkbook.getSheet(0);
            for (int i = 0; i < row; i++) {
                mStrings.add(mSheet.getCell(column, i).getContents());
                DebugLog.e(i + " : " + mSheet.getCell(column, i).getContents());
                mWorkbook.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mStrings;
    }

    public static int UpdateExecl(String path, int column, int row, String content) {
        try {
            Workbook mWorkbook = Workbook.getWorkbook(new File(path));
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File("copy"), mWorkbook);
            WritableSheet mSheet = writableWorkbook.getSheet(0);
            Label label = new Label(column, row, content);
            mSheet.addCell(label);
            writableWorkbook.write();
            writableWorkbook.close();
            return 1;
        } catch (IOException | BiffException | WriteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int createExecl(String path) {
        try {
            WritableWorkbook book = Workbook.createWorkbook(new File(path));
            WritableSheet sheet = book.createSheet("第一页", 0);
            Label label = new Label(0, 0, "");
            sheet.addCell(label);
            book.write();
            book.close();
            return 1;
        } catch (IOException | WriteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
