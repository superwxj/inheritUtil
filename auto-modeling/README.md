# 使用说明

* 当前快照版本: 1.0-SNAPSHOT

* 当前正式版本: 暂无

应用中需要使用基于阿里云ONS实现的消息队列时, 应加入如下依赖(Gradle):

```
  compile 'com.dingdatech:ddlib-mq-ons:1.0-SNAPSHOT'
```

> **Notice:**
>
> * ONS 的 **Topic** 及其 **生产者** 和 **消费者** 都需要事先在阿里云的控制台创建好, 然后在代码中方可使用.

## 阿里云 AccessKey 配置

首先需要包含阿里云AccessKey的配置.

配置文件路径: `classpath:config/aliyun.properties`

配置项格式示例:

```
################################
# Aliyun Configuration
################################
aliyun.access_key=pljMtZlgIBsUyQI9
aliyun.access_secret=DlSgu6EF9LUz1Mi0tJmDKWJpqy1vlK
```

## Producer

消息生产者可通过其 FactoryBean 直接创建, 需要传入对应的 `producerId` 属性.

配置文件路径: `classpath:config/aliyun-ons.properties`

Spring 容器内的配置示例:

```
    <import resource="context-aliyun.xml"/>

    <bean id="testProducer" class="com.dingdatech.lib.mq.ons.OnsProducerFactoryBean">
        <property name="producerId" value="${ons.producer_test}"/>
    </bean>
```

> 其中 `context-aliyun.xml` 已经默认加载了 _config_ 路径下的所有以 _aliyun_ 开头的 properties 文件.

`producerId` 属性的格式为: `PID_YOUR_PRODUCER_ID@Topic_Name`, 例如:

```
ons.producer_test=PID_PNT_TEST_01@pnt_test_topic
```

__已经在 Spring 容器中配置的 Producer 可以直接在应用中获取其示例并通过其 `send(..)` 方法发送消息, 方法的返回值是消息中间件生成的消息ID.__


## Consumer

消息消费者可通过其 FactoryBean 直接创建, 需要传入对应的 `consumerId` 和 `messageHandler` 属性.

配置文件路径: `classpath:config/aliyun-ons.properties`

Spring 容器内的配置示例:

```
    <import resource="context-aliyun.xml"/>

    <bean id="testConsumer" class="com.dingdatech.lib.mq.ons.OnsConsumerFactoryBean">
        <property name="consumerId" value="${ons.consumer_test}"/>
        <property name="messageHandler">
            <bean class="com.dingdatech.lib.mq.ons.OnsMessageHandler"/>
        </property>
    </bean>
```

> 其中 `context-aliyun.xml` 已经默认加载了 _config_ 路径下的所有以 _aliyun_ 开头的 properties 文件.

`consumerId` 属性的格式为: `CID_YOUR_CONSUMER_ID@Topic_Name` 或 `CID_YOUR_CONSUMER_ID@Topic_Name@Filter_Name`, 例如:

```
ons.consumer_test_1=CID_PNT_TEST_01@pnt_test_topic@test
ons.consumer_test_2=CID_PNT_TEST_02@pnt_test_topic
```

__对具体消息的消费, 需要自行实现 `com.dingdatech.lib.mq.api.MessageHandler` 接口, 并对消息消费失败的情况抛出具体的异常信息. 如果消息消费成功,则消息队列将该消息标记为 _已消费_, 如果消费失败, 则消息队列会稍后重新投递该消息, 消费者会继续消费下一条消息.__

