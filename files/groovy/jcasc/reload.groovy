/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2018 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import groovy.json.JsonOutput
import io.jenkins.plugins.casc.*

CasCGlobalConfig config = GlobalConfiguration.all().get(CasCGlobalConfig.class)
String currentConfigPath = config.getConfigurationPath()

String msg = "Success"
Boolean failed = false

if (currentConfigPath != null) {
  try {
    ConfigurationAsCode.get().configure()
  } catch (Exception ex) {
    // set failed to yes
    failed = true
    msg = ex.toString()
  }
} else {
  // fail because no config path is set
  failed = true
  msg = "JCasC as currently no configuration path set! Reloading configuration skipped. Make sure to set a correct configuration path!"
}



def json = JsonOutput.toJson([
  failed: failed,
  msg: msg
])

return json.toString()
