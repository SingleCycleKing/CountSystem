package util;

import java.io.File;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

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
}
