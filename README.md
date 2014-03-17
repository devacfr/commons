commons
=======

Framework Commons contains useful utility modules for following needs:
* **commons-util** module contains global interface as I18n, plugin, process lifecyle, user management, and some utilities as collection builder, compress tools, log, validation and other Spring framework utilities (to remove spring module dependency).
* **commons-testing** module allows to migrate from JUnit3 to JUnit4 easily and integrate directly your favorite mock framework ([Mokito](https://code.google.com/p/mockito/) or [EasyMock](http://easymock.org))
*  **commons-event** is extension of module com.atlassian.event:atlassian-event. it adds EventPublisher Factory, a bean post process registering automatically a listener to receive events and the possibility to customize the concurrent ExecutorService used.


[![Build Status](https://travis-ci.org/devacfr/commons.png)](https://travis-ci.org/devacfr/commons)


## Contribution Policy

Contributions via GitHub pull requests are gladly accepted from their original author.
Along with any pull requests, please state that the contribution is your original work and 
that you license the work to the project under the project's open source license.
Whether or not you state this explicitly, by submitting any copyrighted material via pull request, 
email, or other means you agree to license the material under the project's open source license and 
warrant that you have the legal authority to do so.

## Licence

	This software is licensed under the Apache 2 license, quoted below.
	
	Copyright 2014 Christophe Friederich
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may not
	use this file except in compliance with the License. You may obtain a copy of
	the License at http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	License for the specific language governing permissions and limitations under
	the License.
