prunsrv.exe //IS//Investor ^
--DisplayName="Investor" ^
--Install="D:\projects\codes\intellij\investor\deploy\production\bin\prunsrv.exe" ^
--Jvm="C:\Program Files\Java\jdk-11.0.1\bin\server\jvm.dll" ^
--StartClass=com.investor.Main ^
--JvmOptions=-Duser.language=US;-Duser.region=en;-Dfile.encoding=UTF8 ^
--Classpath=../classes;../lib/* ^
--StartMode=jvm ^
--StartMethod=main ^
--StopMode=jvm ^
--StopClass=Stop ^
--StopMethod=main