package com.plusub.lib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2016/5/12 10:37 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:格式化工具类
 */
public class FormatUtils {
    /**
     * 四舍五入获取double
     * @param value double值
     * @param decimalPlaces 保留的小数点位数，以四舍五入方式
     * @return
     */
    public static double roundingDouble(double value, int decimalPlaces) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.UP);
        return bg.doubleValue();
    }

    /**
     * 保留double小数点位数
     * @param value
     * @param decimalPlaces 保留的小数点位数
     * @return
     */
    public static double formateDouble(double value, int decimalPlaces) {
        BigDecimal bg = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.DOWN);
        return bg.doubleValue();
    }

    /**
     * 四舍五入获取double，返回string
     * @param value
     * @param decimalPlaces 保留的小数点位数
     * @return 返回string
     */
    public static String roundingDouble2(double value, int decimalPlaces) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(decimalPlaces);//保留小数位数
        nf.setRoundingMode(RoundingMode.UP);//如果不需要四舍五入，可以使用RoundingMode.DOWN
        return nf.format(value);
    }

    /**
     * 保留double小数点位数，返回string
     * @param value
     * @param decimalPlaces 保留的小数点位数
     * @return
     */
    public static String formateDouble2(double value, int decimalPlaces) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(decimalPlaces);//保留小数位数
        nf.setRoundingMode(RoundingMode.DOWN);//如果不需要四舍五入，可以使用RoundingMode.DOWN
        return nf.format(value);
    }
}
