INSERT INTO categories (id, name) VALUES(1,"MISC");
INSERT INTO tutorials (id,title,content) VALUES(1,"Welcome to the opensource Tutorial Manager!",COMPRESS("<p>Start off by installing Tomcat</p><pre class='prettyprint lang-bash'>#!/bin/bash</br>$ sudo apt-get install tomcat7</pre>"));
INSERT INTO tutorial_categories (tutorial_id,category_id) VALUES(1,1);
