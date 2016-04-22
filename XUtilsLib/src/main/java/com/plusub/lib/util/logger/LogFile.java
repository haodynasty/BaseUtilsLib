package com.plusub.lib.util.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Copyright (C) quhao All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com>
 * date     : 2016/4/21 15:56
 * last modify author : blakequ 2016/4/21 15:56 first add this class
 * version : 1.0
 * description:
 */
public class LogFile {

    /**
     * write log to file
     * @param tag
     * @param message
     * @return
     */
    public static boolean log(String tag, String message){
        //1.检查sdcard是否正常

        //2.检查上次日志文件是否存在，如果存在大小是否超过限制（超过则新建，否则添加）;如果不存在则新建日志文件
        //日志文件命名规则：日期(当天0：0：0)_增长序列index(1,2,...)，目的是方便查找当前文件
        //日志保存到默认命名app路径下

        //3.写日志到文件
        return false;
    }

    private static boolean save(File dic, String fileName, String msg) {
        File file = new File(dic, fileName);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg);
            outputStreamWriter.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
