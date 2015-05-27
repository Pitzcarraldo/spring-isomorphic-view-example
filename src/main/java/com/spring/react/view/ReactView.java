/*
 * Copyright 2015 Minkyu Cho
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spring.react.view;

import com.google.common.collect.Maps;
import de.matrixweb.jreact.JReact;
import lombok.Setter;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

/**
 * This is the spring view use to generate the content based on
 * a React template.
 *
 * @author Minkyu Cho
 */
@Setter
public class ReactView extends AbstractTemplateView {
	private String viewName;
	private JReact react;

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> origin, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		Map<String, Object> model = Maps.newHashMap();
		for (String key : origin.keySet()) {
			if (origin.get(key) instanceof RequestContext) {
				continue;
			}
			model.put(key, origin.get(key));
		}
		response.setContentType(getContentType());
		final Writer writer = response.getWriter();
		try {
			writer.append(react.renderToString(viewName, model));
		} finally {
			writer.flush();
		}
	}
}