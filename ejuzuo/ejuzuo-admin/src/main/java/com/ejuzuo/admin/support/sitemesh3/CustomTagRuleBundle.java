package com.ejuzuo.admin.support.sitemesh3;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * sitemesh添加自定义标签
 * @author xiaoqing.chen
 */
public class CustomTagRuleBundle implements TagRuleBundle {
	@Override
	public void cleanUp(State arg0, ContentProperty arg1, SiteMeshContext arg2) {
	}

	@Override
	public void install(State arg0, ContentProperty arg1, SiteMeshContext arg2) {
		arg0.addRule("footer-scripts", new ExportTagToContentRule(arg2, arg1.getChild("footer-scripts"), false));
	}
}
