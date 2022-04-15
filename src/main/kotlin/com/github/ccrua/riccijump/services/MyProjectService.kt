package com.github.ccrua.riccijump.services

import com.intellij.openapi.project.Project
import com.github.ccrua.riccijump.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
