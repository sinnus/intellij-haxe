/*
 * Copyright 2000-2013 JetBrains s.r.o.
 * Copyright 2014-2014 AS3Boyan
 * Copyright 2014-2014 Elias Ku
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
package com.intellij.plugins.haxe.ide.hierarchy.type;

import com.intellij.ide.hierarchy.HierarchyNodeDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Created by srikanthg on 10/23/14.
 */
public class HaxeTypeHierarchyTreeStructure extends HaxeSubtypesHierarchyTreeStructure {

  private static final Logger LOG = Logger.getInstance("#com.intellij.plugins.haxe.ide.hierarchy.type.HaxeTypeHierarchyTreeStructure");

  public HaxeTypeHierarchyTreeStructure(final Project project, final PsiClass aClass, String currentScopeType) {
    super(project, buildHierarchyElement(project, aClass), currentScopeType);
    setBaseElement(myBaseDescriptor); //to set myRoot
  }

  private static HierarchyNodeDescriptor buildHierarchyElement(final Project project, final PsiClass aClass) {
    HierarchyNodeDescriptor descriptor = null;
    final PsiClass[] superClasses = getSuperTypesAsArray(aClass);
    for(int i = superClasses.length - 1; i >= 0; i--){
      final PsiClass superClass = superClasses[i];
      final HierarchyNodeDescriptor newDescriptor = new HaxeTypeHierarchyNodeDescriptor(project, descriptor, aClass, false);
      if (descriptor != null){
        descriptor.setCachedChildren(new HierarchyNodeDescriptor[] {newDescriptor});
      }
      descriptor = newDescriptor;
    }
    final HierarchyNodeDescriptor newDescriptor = new HaxeTypeHierarchyNodeDescriptor(project, descriptor, aClass, true);
    if (descriptor != null) {
      descriptor.setCachedChildren(new HierarchyNodeDescriptor[] {newDescriptor});
    }
    return newDescriptor;
  }

}
