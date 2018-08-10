package io.xunyss.commons.exec.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.xunyss.commons.exec.StreamHandler;
import io.xunyss.commons.io.IOUtils;

/**
*
* @author XUNYSS
*/
public abstract class LineReadHandler extends StreamHandler {
	
	// TODO: support encoding
	// TODO: error stream
	
	
	@Override
	public void start() {
		
		pre();
		
		InputStream inputStream = getProcessInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				processLine(line);
			}
		}
		catch (Exception ex) {
			////////////////////////////////////////////////////////////////////////////////////////
			// TODO
			////////////////////////////////////////////////////////////////////////////////////////
			ex.printStackTrace();
		}
		finally {
			IOUtils.closeQuietly(reader);
		}
		
		post();
	}

	@Override
	public void stop() {
		
	}
	
	
	public /* abstract */ void pre() {
		
	}
	
	public /* abstract */ void post() {
		
	}
	
	public abstract void processLine(String line);
}
