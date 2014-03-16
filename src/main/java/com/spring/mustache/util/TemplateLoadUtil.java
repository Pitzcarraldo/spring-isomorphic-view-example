package com.spring.mustache.util;


import com.github.jknack.handlebars.io.TemplateLoader;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateLoadUtil {
    @Autowired
    private TemplateLoader templateLoader;

    @Autowired
    private ResourceLoader resourceLoader;

    private class Template {
        private String id;
        private String content;

        public Template(String id, String content) {
            this.id = id;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    private Template getTemplate(String viewName) {
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(getReader(viewName), writer);
            return new Template(viewName.replace("/", "_"), writer.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Template> getTemplates(String... viewNames) {
        List<Template> templates = new ArrayList<Template>();
        for (String viewName : viewNames) {
            templates.add(getTemplate(viewName));
        }
        return templates;
    }

    private Reader getReader(String filename) throws Exception {
        if (!filename.startsWith(templateLoader.getPrefix())) {
            filename = templateLoader.getPrefix() + filename;
        }
        if (!filename.endsWith(templateLoader.getSuffix())) {
            filename = filename + templateLoader.getSuffix();
        }
        Resource resource = resourceLoader.getResource(filename);
        if (resource.exists()) {
            return new InputStreamReader(resource.getInputStream(), Charset.forName("UTF-8"));
        }
        throw new FileNotFoundException(filename);
    }
}
