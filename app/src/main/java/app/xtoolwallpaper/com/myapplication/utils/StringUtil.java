/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package app.xtoolwallpaper.com.myapplication.utils;

/**
 * 通用字符串(String)相关类,为null时返回""
 *
 * @author Lemon
 * @use StringUtil.xxxMethod(...);
 */
public class StringUtil {
    private static final String TAG = "StringUtil";

    public StringUtil() {
    }

    private static String currentString = "";

    /**
     * 获取刚传入处理后的string
     *
     * @return
     * @must 上个影响currentString的方法 和 这个方法都应该在同一线程中，否则返回值可能不对
     */
    public static String getCurrentString() {
        return currentString == null ? "" : currentString;
    }

    /**
     * 获取string,为null则返回""
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 获取去掉前后空格后的string,为null则返回""
     *
     * @param s
     * @return
     */
    public static String getTrimedString(String s) {
        return getString(s).trim();
    }

    /**
     * 判断字符是否非空
     *
     * @param s
     * @param trim 是否剔除前导空白字符和尾部空白字符
     * @return
     */
    public static boolean isNotEmpty(String s, boolean trim) {
        if (s == null) {
            return false;
        }
        if (trim) {
            //从当前 String 对象移除所有前导空白字符和尾部空白字符。
            s = s.trim();
        }
        if (s.length() <= 0) {
            return false;
        }
        currentString = s;
        return true;
    }

}
