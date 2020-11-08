# MrnDesign project
<br>
My first fully usable web application  <br>
It is may personal website <br>
with graphics and IT projects <br>
<br>
It is deployed at:
<html><a href="https://mrndesign.eu/">https://mrndesign.eu</a></html>
<br>
Check it out and dont forget to send a feedback !! ;-)

## Usage

```
You can start watching my graphics and projects immediately
For more functions you should register and log in


While login you can check remember me, 
to stay logged for some time even if the browser is closed

As an admin you can additionally manage users.
Easily add new graphics, sending files to ftp server

Shop is in progress, so it is unavailable for users

Check it out and send me feedback with a message form!!!


```

In order for the program to work, with your own database
you have to add to application.properties file all necessary data:

```
*** Database connection ***
spring.datasource.username
spring.datasource.password
spring.datasource.url
spring.jpa.properties.hibernate.dialect
spring.datasource.driver-class-name

*** Mail data ***
spring.mail.username
spring.mail.password
spring.mail.host
spring.mail.port
spring.mail.properties.mail.transport.protocol
spring.mail.properties.mail.smtp.port
spring.mail.properties.mail.smtp.auth
spring.mail.properties.mail.smtp.starttls.enable
spring.mail.properties.mail.smtp.starttls.required

*** domain url or if locally - localhost:8080 (or with other port) ***
www.domain.url

*** secret key for session tokens ***
secret.key.for.session.token

*** default admin user ***
default.admin.username
default.admin.password

*** ftp server connection ***
files.path
tmp.files.path
ftp.server.host
ftp.server.password
ftp.server.path
ftp.server.port
ftp.server.user

*** captcha settings ***
google.recaptcha.key.site
google.recaptcha.key.secret
google.recaptcha.url

*** default users ***
default.admin.username
default.admin.password
default.user.username
default.user.password
```

It is in development stage, so look forward for future features:

```
Future TODO is -> shop and sales management
```



# License
  Copyright 2020 Mateusz Niedbał
  
  contact:  mat.niedbal6@gmail.com



