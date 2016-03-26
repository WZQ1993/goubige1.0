package com.wangziqing.goubige.utils;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.AsyncTask;

public class UploadHeader extends AsyncTask<Void, Void, String> {
	private UploadHeaderInterface uploadHeaderCallback;
	private String request_url = "";
	private String pic_url;
	private String userId;
	// private String newName = "image.jpg";
	// private String uploadFile = "/sdcard/image.JPG";
	String end = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";

	public UploadHeader(String userId, String request_url, String pic_url,
			UploadHeaderInterface uploadHeaderCallback) {
		this.request_url = request_url;
		this.pic_url = pic_url;
		this.userId = userId;
		this.uploadHeaderCallback = uploadHeaderCallback;
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(request_url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			// *****为分隔符，用来区分多个请求
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			// name为服务器提取头像文件的依据，filename为头像文件的名字（包含后缀名）
		/*	dos.writeBytes("Content-Disposition: form-data; " + "name=\""
					+ userId + ";filename=" + pic_url.hashCode() + ".png"
					+ "\"" + end);*/
			dos.writeBytes("Content-Disposition: form-data;name=\""+userId+"\";filename=\""+pic_url.hashCode()+".png"+"\""+end);
			// + "name=\"file1\";filename=\"" + pic_url.hashCode()+".png" + "\""
			// + end);
			dos.writeBytes(end);
			FileInputStream fStream = new FileInputStream(pic_url);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				dos.write(buffer, 0, length);
			}
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			dos.flush();
			/* 取得Response内容 */
			InputStream is = conn.getInputStream();
			int ch;
			StringBuffer sb = new StringBuffer();
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
			dos.close();
			//返回服务器返回的数据
			return sb.toString().trim();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		// 返回服务器数据给请求方
		uploadHeaderCallback.completeUpload(result);
	}

}
