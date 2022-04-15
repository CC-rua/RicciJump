package com.github.ccrua.riccijump.template;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import org.jetbrains.annotations.NotNull;

public class JavaTemplateContext extends TemplateContextType {

    protected JavaTemplateContext() {
        super("JAVA_CODE", "Java");
    }

    @Override
    public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
        return templateActionContext.getFile().getName().endsWith(".java");
    }
}
