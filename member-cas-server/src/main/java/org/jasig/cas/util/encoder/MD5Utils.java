/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.util.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class MD5Utils {
	static final Logger LOGGER = LoggerFactory.getLogger(MD5Utils.class);
	/**
	 * 对文件全文生成MD5摘要
	 * @param file 要加密的文件
	 * @return MD5摘要码
	 */

	public static String getMD5(File file) {
		FileInputStream fis = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fis.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] b = md.digest();
			return HexUtils.toHexStr(b);
			// 16位加密
		} catch (Exception ex) {
			LOGGER.error("",ex);
			return null;
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException ex) {
				LOGGER.error("",ex);
			}
		}
	}

	/**
	 * 对输入流生成MD5摘要
	 * @param fileInputStream 文件输入流 要加密的文件
	 * @return MD5摘要码
	 */
	public static String getMD5(FileInputStream fileInputStream) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = fileInputStream.read(buffer)) != -1) {
				md.update(buffer, 0, length);
			}
			byte[] b = md.digest();
			return HexUtils.toHexStr(b);
		} catch (Exception ex) {
			LOGGER.error("",ex);
			return null;
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException ex) {
				LOGGER.error("",ex);
			}
		}
	}
	/**
	 * source.getBytes(utf-8)
	 * @param source
	 * @return
	 */
	public static String getMD5(String source){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return HexUtils.toHexStr( md.digest(source.getBytes("UTF-8")));
		} catch (Exception ex) {
			LOGGER.error("",ex);
			return null;
		}
	}
	
	public static String getMD5(String source,String character){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return HexUtils.toHexStr( md.digest(source.getBytes(character)));
		} catch (Exception ex) {
			LOGGER.error("",ex);
			return null;
		}
	}

}
