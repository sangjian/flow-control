![Java CI with Maven](https://github.com/sangjian/process-control/workflows/Java%20CI%20with%20Maven/badge.svg)

## 简介

一个轻量级的流程控制框架，使用流程图的方式，解决了业务代码臃肿以及难于理解的情况，通过流程节点将业务逻辑更加精细化，使业务流程能够清晰易懂，同时便于代码复用，能够以更加灵活的方式实现具体的业务逻辑。

## 流程实例

一个流程实例表示一个具体的流程，包括了对节点的添加、执行等操作。

## 节点

节点是对流程中具体执行模块的一个抽象，根据不同的节点类型，具体的执行逻辑也不同。

### 通用执行节点

最简单的节点，只执行业务逻辑，执行完毕则返回，流程实例根据返回值（true/false）判断流程是否继续。

### 条件节点

条件节点也是执行节点，但有自己的执行逻辑，不执行具体的业务逻辑，而是负责流程的控制，主要有一下三种：

1. if节点
2. while节点
3. doWhile节点

其中，if节点相当于是一个分支节点，它包括两个分支：if分支和else分支，执行的时候根据具体的规则来选择应该执行哪个分支；while和doWhile类似，只有一个分支，通过规则判断是否应该循环执行该分支。

### 传递节点

有时候我们需要对业务逻辑执行后的返回值进行处理，那么可以使用传递节点。一个传递节点可以返回具体的结果，并通过链式注册的结果处理器来依次对结果进行处理。

### 分支节点

1. 普通分支节点：可包括多个执行节点，分支中节点按顺序执行；
2. 并行分支节点：包括多个普通分支，并行执行各个普通分支。

### 可聚合节点

表示节点的返回结果是可以与其他节点的返回结果进行聚合的。

### 聚合节点

聚合节点会把注册到该节点上所有节点的执行结果进行聚合。

### 分组节点

分组节点也是一个分支节点，不同的是，分组有Block来进行上下文隔离。

### Try-Catch-Finally节点

该节点会注册3个分支：

1. try分支：正常的业务逻辑，如果有异常，则不会向下执行，直接跳转到catch分支；
2. catch分支：处理异常，可以有多个分支，根据不同的异常类型来处理，例如一个分支用来处理NullPointerException，一个分支用来处理IllegalArgumentException；
3. finally分支，最终执行分支，无论是否有异常，最终都会执行该分支上的节点。

## 分支

分支是一组节点的集合。

## 流程上下文

流程上下文会在整个流程中进行传递，直到流程结束，可用于变量的传递以及上下文隔离等。

## Block

用于对不同的范围进行上下文隔离，比如一组节点中的变量无需注册到流程上下文中，可以用Block来进行隔离，这样分组内部所使用的变量不会注册到流程上下文中，但在分组内部可以通过Block来共享。

## 规则

执行节点前会有规则校验，如果校验失败，则不会执行该节点。

## 通用执行器

用于执行一个分支，分为串行执行器和并行执行器。

## 聚合执行器

用于执行并合并多个可聚合节点的返回结果，分为串行聚合执行器和并行聚合执行器。

## 聚合器

用于合并多个可聚合节点的返回结果。

## 并行完成策略

用于并行节点，根据不同的完成策略来决定是否以及何时才可继续执行下一个节点。

1. 所有分支执行完成；
2. 所有分支执行完成并全部返回值都表示继续执行（false）；
3. 至少一个分支执行完成；
4. 至少一个分支执行完成并且返回值表示继续执行（false）。

## 结果流

表示结果处理的流程，在结果流上可注册不同的结果处理器来对结果进行处理。

## 结果处理器

用于对结果进行处理。

## 异常处理器

节点执行时，如果设置了异常处理器，则会通过异常处理器处理异常，否则向上抛出。