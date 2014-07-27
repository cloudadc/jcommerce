/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jcommerce.gwt.client.resources;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * The images used throughout the Showcase.
 */
public interface iShopImages extends ImageBundle {
  AbstractImagePrototype catWidgets();

  AbstractImagePrototype button_bg();

  /**
   * Indicates the locale selection box.
   */
  AbstractImagePrototype locale();

  AbstractImagePrototype icon_search();
  
  AbstractImagePrototype icon_copy();
  
  AbstractImagePrototype gwtLogo();
  
  AbstractImagePrototype is_read();
  
  AbstractImagePrototype not_read();
  
  AbstractImagePrototype view_log();
  
  AbstractImagePrototype assign_role();

  @Resource("no.gif")
  AbstractImagePrototype no();
}