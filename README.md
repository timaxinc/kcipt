# kcipt

##targets of kcipt

###what

- create a easy to use library for compiling and executing kotlin scripts on the fly

###how

- create a compiler and a executor using kotlin's scripting api

###targeted usage

####compile and execute 

#####from kotlin
````kotlin
//create a FileSource that can be read by the script-compiler
val scriptSource = FileSource("path/to/example/script/example_script.script.extension") 

//compile and execute script from scriptSource with a given context(ExampleScriptContext)
val result: ExampleScriptContext = executeScript(scriptSource, ExampleScriptContext())
````

#####content of path/to/example/script/example_script.script.extension
````kotlin
//script content is compiled inside a code block with context(ExampleScriptContext) as receiver
//with(context) {
//    scriptContent
//}

script {
    //load dependencies
    //configure script 
    dependecies {
        //load a library into script classpath
        script("exampleinc:exampellib:1.0.2")    
    }
}

//this is the ExampleScriptContext object
someMethodCalledOnTheScriptContextObject()
````