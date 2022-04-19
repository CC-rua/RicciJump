package com.github.ccrua.riccijump.ui;

import com.intellij.ide.util.newProjectWizard.AbstractProjectWizard;
import com.intellij.ide.util.newProjectWizard.StepSequence;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @description:
 * @author: ricci
 * @date: 2022-04-19 16:26:22
 */
public class NewAlproDialog extends AbstractProjectWizard {
    private final StepSequence _m_Sequence;
    private Project _m_Project;
    private Module _m_Module;

    public NewAlproDialog(Project _project, @Nullable Module _module) {
        super("alpro 生成", _project, "");
        this._m_Project = _project;
        this._m_Module = _module;
        this._m_Sequence = new StepSequence();

        for (ModuleWizardStep wizardStep : createWizardSteps()) {
            _m_Sequence.addCommonStep(wizardStep);
            addStep(wizardStep);
        }
        init();
    }

    @Override
    public StepSequence getSequence() {
        return _m_Sequence;
    }

    public ModuleWizardStep[] createWizardSteps() {
        return new ModuleWizardStep[]{

        };
    }
}
