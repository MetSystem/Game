使用资源文件中的常量有2种方式.第1种是通过xml使用,方法为
@[package_name:]<resource_type>/<resource_name>
如 android:horizontalSpacing="@edu.cgxy.resvalue:dimen/spacing"
或者 android:horizontalSpacing="@dimen/spacing"
第2种是通过Java代码使用,方法为
[<package_name.>].R.<resource_type>.<resource_name>
如 R.string.c1
或者 edu.cgxy.resvalue.R.string.c1

github push不用每次都输入密码但是貌似不行,再试一次,再试一次,再试一次，再试一次,再试一次,再试一次