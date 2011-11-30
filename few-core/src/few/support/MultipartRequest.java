package few.support;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 17.11.11
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class MultipartRequest extends HttpServletRequestWrapper {

	private static final Logger logger = Logger.getLogger(MultipartRequest.class.getName());

	private Map<String, List<String>> formParams;

	private Map<String, List<FileItem>> fileParams;

    private Map<String, String[]> parameterMap;

	public MultipartRequest(HttpServletRequest request, ServletFileUpload servletFileUpload) throws IOException {
		super(request);
		formParams = new LinkedHashMap<String, List<String>>();
		fileParams = new LinkedHashMap<String, List<FileItem>>();

		parseRequest(request, servletFileUpload);
	}

	@SuppressWarnings("unchecked")
	private void parseRequest(HttpServletRequest request, ServletFileUpload servletFileUpload) throws IOException {
		try {
			List<FileItem> fileItems = servletFileUpload.parseRequest(request);

			for(FileItem item : fileItems) {
				if(item.isFormField())
					addFormParam(item);
				else
					addFileParam(item);
			}

		} catch (FileUploadException e) {
			logger.severe("Error in parsing fileupload request");

			throw new IOException(e.getMessage());
		}
	}

	private void addFileParam(FileItem item) {
		if(fileParams.containsKey(item.getFieldName())) {
			fileParams.get(item.getFieldName()).add(item);
		} else {
			List<FileItem> items = new ArrayList<FileItem>();
			items.add(item);
			fileParams.put(item.getFieldName(), items);
		}
	}

	private void addFormParam(FileItem item) {
		if(formParams.containsKey(item.getFieldName())) {
			formParams.get(item.getFieldName()).add(item.getString());
		} else {
			List<String> items = new ArrayList<String>();
			items.add(item.getString());
			formParams.put(item.getFieldName(), items);
		}
	}

	@Override
	public String getParameter(String name) {
		if(formParams.containsKey(name)) {
			List<String> values = formParams.get(name);
			if(values.isEmpty())
				return "";
			else
				return values.get(0);
		}
		else {
			return super.getParameter(name);
		}
	}

	@Override
	public Map getParameterMap() {
        if(parameterMap == null) {
            Map<String,String[]> map = new LinkedHashMap<String, String[]>();

            for(String formParam : formParams.keySet()) {
                map.put(formParam, formParams.get(formParam).toArray(new String[0]));
            }

            map.putAll(super.getParameterMap());

            parameterMap = Collections.unmodifiableMap(map);
        }

		return parameterMap;
	}

	@Override
	public Enumeration getParameterNames() {
		Set<String> paramNames = new LinkedHashSet<String>();
        paramNames.addAll(formParams.keySet());
        paramNames.addAll(fileParams.keySet());

        Enumeration<String> original = super.getParameterNames();
        while(original.hasMoreElements()) {
            paramNames.add(original.nextElement());
        }

		return Collections.enumeration(paramNames);
	}

	@Override
	public String[] getParameterValues(String name) {
		if(formParams.containsKey(name)) {
			List<String> values = formParams.get(name);
			if(values.isEmpty())
				return new String[0];
			else
				return values.toArray(new String[values.size()]);
		}
		else {
			return super.getParameterValues(name);
		}
	}

	public FileItem getFileItem(String name) {
		if(fileParams.containsKey(name)) {
			List<FileItem> items = fileParams.get(name);

			return items.get(0);
		} else {
			return null;
		}
	}
}