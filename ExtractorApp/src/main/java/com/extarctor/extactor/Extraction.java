package com.extarctor.extactor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.exceljson.convert.ExcelToJsonConverter;
import com.exceljson.convert.ExcelToJsonConverterConfig;
import com.exceljson.pojo.ExcelWorkbook;
import com.jsontocsv.parser.JSONFlattener;
import com.jsontocsv.writer.CSVWriter;

@Component
public class Extraction {
	private static final String baseLocation="/home/qion/Documents/rakshit";
	
	private static final  String zipFilePath=baseLocation+"/poc.zip";
	private static final  String destDirectory=baseLocation;
    public void extract(){
		try{
		UnzipUtility.unzip(zipFilePath, destDirectory);
		File [] file=Extraction.getXlSheet(destDirectory+"/poc");
		if(file.length>0){
			String json =xltoJson(file[0].getAbsolutePath());
			/*
	         *  Parse a JSON String and convert it to CSV
	         */
			
			JSONObject joJsonObject=new JSONObject(json);
			JSONArray sheets= joJsonObject.getJSONArray("sheets");
			JSONArray data= ((JSONObject)sheets.get(0)).getJSONArray("data");
			//System.out.println(joJsonObject);
	        List<Map<String, String>> flatJson = JSONFlattener.parse(data);
	        // Using the default separator ','
	        CSVWriter.writeToFile(CSVWriter.getCSV(flatJson), destDirectory+"/poc/poc.csv");

		}
		}catch(Exception exception){
			System.out.println("Exception:"+exception.getStackTrace());
		}
		
	}
	
	
public static File[] getXlSheet(String directory){
		File dir = new File(directory);

		File[] matches = dir.listFiles(new FilenameFilter()
		{
		  public boolean accept(File dir, String name)
		  {
		     return name.startsWith("Exce") && name.endsWith(".xlsx");
		  }
		});
		return matches;
	
	}
	
public static String xltoJson(String sourceFile) throws Exception{
	String json=null;
	 {
			//options.addOption("s", "source", true,sourceFile);
			//options.addOption("df", "dateFormat", true, "The template to use for fomatting dates into strings.");
			//options.addOption("?", "help", true, "This help text.");
			//options.addOption("n", "maxSheets", true, "Limit the max number of sheets to read.");
			//options.addOption("l", "rowLimit", true, "Limit the max number of rows to read.");
			//options.addOption("o", "rowOffset", true, "Set the offset for begin to read.");
			//options.addOption(new Option("percent", "Parse percent values as floats."));
			//options.addOption(new Option("empty", "Include rows with no data in it."));
			//options.addOption(new Option("pretty", "To render output as pretty formatted json."));
			//options.addOption(new Option("fillColumns", "To fill rows with null values until they all have the same size."));
			
			
			ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();
			config.setSourceFile(sourceFile);
			/*String valid = config.valid();
			if(valid!=null) {
				System.out.println(valid);
				//help(options);
				return null;
			}
			*/
			ExcelWorkbook book = ExcelToJsonConverter.convert(config);
			json = book.toJson(config.isPretty());
			System.out.println(json);
	    }
	return json;
}
	
	
}
