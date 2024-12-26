# 基于TarsoDSP库的调音器和节拍器

## 项目简介
BUAA计算机学院选修课程——Java程序设计课的大作业， 使用纯Java语言设计的一个小型程序，

该程序使用Java Swing构造了简单的可视化界面，可以实现调音器和节拍器这两个简单的功能。

设计灵感来源[本科生尝试安卓APP开发：手机调音器节拍器（音乐学习者使用）](https://blog.csdn.net/LK_muchen/article/details/128649838)

### 调音器

检测麦克风输入音频的频率，根据当前选择的乐器匹配最接近的音名，并计算出偏差音分

可以选择不同的乐器，它们提供了不同的音域选择

### 节拍器

根据指定的bpm发出规律性的节拍声

可以选择不同的音色和bpm

## 项目依赖

使用maven管理项目，使用的第三方库包括`TarsoDSP`和`Flatlaf`

maven配置如下
```Xml
<project>
    
    <!-- Maven 仓库配置 -->
    <repositories>
        <repository>
            <id>be.0110.repo-releases</id>
            <name>0110.be repository</name>
            <url>https://mvn.0110.be/releases</url>
        </repository>
    </repositories>
    
    <!-- 依赖项配置 -->
    <dependencies>
        <!-- flatlaf 依赖 -->
        <dependency>
            <groupId>com.formdev</groupId>
            <artifactId>flatlaf</artifactId>
            <version>3.5.4</version>
        </dependency>
        <!-- TarsosDSP 依赖 -->
        <dependency>
            <groupId>be.tarsos.dsp</groupId>
            <artifactId>core</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>be.tarsos.dsp</groupId>
            <artifactId>jvm</artifactId>
            <version>2.5</version>
        </dependency>
    </dependencies>
    
</project>
```

### TarsoDSP库

TarsoDSP库是一个强大的音频数字信号处理的库，它基于纯Java实现，提供了很多处理音频的库。

### Flatlaf库

FlatLaf 是一个现代化、开源的 Java Swing 外观库，旨在提供简洁、现代且高效的用户界面。

## 项目结构

我在`src/main/java`下主要实现了三个软件包，分别是`Application`,`DealSound`,`Instrument`

分别实现所需要的不同功能

### Application

主要实现可视化窗口的逻辑，主页面`MainWindow`，其下又关联了其它具体的窗口

主要包括`TunerWindow`和`MetronomeWindow`，分别实现调音器窗口和节拍器窗口

### DealSound

对音频进行处理的类方法，实现了`PitchDetector`类获得音高

这一过程需要进行快速傅里叶变换和多次滤波，我们创建了相应的类进行实现

`SoundGenerator`和`VoiceChanger`类分别是关于产生指定频率声音和指定频率变声的类

目前还没有完全实现，预计以后(如果有时间)会进一步实现

### Instrument

乐器类，主要实现了父类`Instrument`和一众子类

父类没有实现构造器(构造器无代码)，子类实现自己的构造器并覆写父类的`getName()`方法

## 项目运行

克隆源码并配置好相关依赖后
```shell
https://github.com/7fenfen/Tuner.git
```
运行`MainWindow`文件即可

此外，我们也提供了打包好的jar文件`Tuner.jar`

## 感想

在完成这个Java大作业后，我对于Java项目的开发流程有了更深刻的了解

通过在实际的应用场景中使用继承和接口等特性，我更加理解了Java的多态在实际开发中的重要作用

而这一次的开发，其实不只是简单地完成作业，也是实现了我一直想做的一个小程序

所以如果未来有机会的话，我会把这个项目继续做完，继续添加一些功能，并且设置一个更精美的界面