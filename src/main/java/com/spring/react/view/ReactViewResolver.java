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

import de.matrixweb.jreact.JReact;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileNotFoundException;

/**
 * @author Minkyu Cho
 */
@Getter
@Setter
public class ReactViewResolver extends AbstractTemplateViewResolver implements ViewResolver, InitializingBean {
	private static final String SCRIPT_ENGINE_NAME = "nashorn";
	private static final ScriptEngine DEFAULT_ENGINE = new ScriptEngineManager().getEngineByName(SCRIPT_ENGINE_NAME);
	private JReact react;

	public ReactViewResolver() {
		setViewClass(ReactView.class);
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		ReactView view = (ReactView) super.buildView(viewName);
		try {
			view.setViewName("./" + viewName + getSuffix());
			view.setReact(react);
			return view;
		} catch (Exception e) {
			throw new FileNotFoundException(view.getUrl() + " : " + e.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String viewPath = getServletContext().getRealPath(getPrefix());
		String modulePath = viewPath + "/node_modules";
		react = new JReact(DEFAULT_ENGINE);
		react.addRequirePath(viewPath);
		react.addRequirePath(modulePath);
	}

	@Override
	protected Class<?> requiredViewClass() {
		return ReactView.class;
	}
}