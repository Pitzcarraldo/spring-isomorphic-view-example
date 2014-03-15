package com.spring.mustache.util;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.mustache.MustacheTemplateLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateLoadUtil {
    @Autowired
    private MustacheTemplateLoader templateLoader;

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
            IOUtils.copy(templateLoader.getTemplate(viewName), writer);
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
}
