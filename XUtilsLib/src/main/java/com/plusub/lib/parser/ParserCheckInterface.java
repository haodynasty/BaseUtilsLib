/*
 * FileName: EntityCheckInterface.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2015-4-19 下午3:28:41
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.parser;

import org.json.JSONObject;

import com.plusub.lib.util.CommException;

/**
 * @ClassName: EntityCheckInterface
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-19 下午3:28:41<br>
 *     <b>最后修改时间：</b>2015-4-19 下午3:28:41
 * @version v1.0
 */
public interface ParserCheckInterface {
	
	/**
	 * 服务器返回JSON数据错误检查（在接收到服务器返回数据之前，先判断返回数据是否正确，正确在使用）
	 * <br><b>必须实现，否则无法解析正确的数据，如果不正确，必须抛出CommonException异常</b>
	 * <p>Title: checkError
	 * <p>Description: 
	 * @param jo
	 * @return JSON数据是否正确，不正确返回false，正确返回true
	 * @throws CommException 如果不正确必须抛出异常，指明异常码:{@link #ErrorCode}}
	 */
	public boolean checkJsonError(JSONObject jo) throws CommException;
}
