#JMessage Demo

## Demo简介
此Demo简化了上层UI的实现，目的仅仅是为了展示JMessage android sdk各接口的用法。
如果需要完整的带有UI实现的demo，请参考[JChat in Github](https://github.com/jpush/jchat-android/)以及[android UI-Kit](https://github.com/jpush/jmessage-android-uikit)
(需要注意的是jchat在对sdk接口的实现上和demo相比可能会有滞后)

## Demo导入步骤
+ 导入Android Studio：
 - 在你的Project中选择File->Import Module->demo的路径->finished
 - 打开后请按照AndroidManifest的提示替换您的包名和APPKey
 - 并全局替换R文件，具体操作如下：
  Edit->Find->Replace in Path，将:  
  import im.sdk.debug.R;  
  替换为  
  import 您的包名.R;  
  在Scope栏中，选中Module，选择demo。最后确认
 - clean，然后build。

+ 导入Eclipse：
 - 打开后请按照AndroidManifest的提示替换您的包名和APPKey
 - 选中该项目按Ctrl + H（全局搜索） , 在弹出的对话框中选择File Search-->在Containing text中填写：
import im.sdk.debug.R; （别忘了分号）
 - 点击Replace在弹出的对话框中，全局将：  
 import im.sdk.debug.R;  
 替换为  
 import 您的包名.R;
 - clean，然后build。


