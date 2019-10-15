# Contributing

We would love, if you plan to contribute to this project in any way. However there are a few things you should consider before opening an issue or submitting a pull request. We know we have some conventions that may seem unusual to you, but they help us to ensure a high code quality and offer many other benefits. Don't be scared by the extent of our policies, we can assure you they may seem harder than they are.

## Issues

If you find a bug or simply have a feature request, let us know with an issue. Before submitting your issue please check that it complies with the following requirements.

### Templates

First of all, please use our templates. The different sections in it were chosen after careful consideration and are especially crucial, if your issue is a bug report, as the information we ask you to provide could possibly play a major role in our debugging process.

### Naming Convention

Furthermore we have a special naming scheme for our issue titles that helps us assigning certain issues to team members and significantly improve the general view when looking at our issues.

#### Bugs

If your issue is a bug report, the title should start with `fix: ` followed by a concise, but short description of the bug you have encountered. If the bug is related to a specific class, you can add a parentheses after the `fix` in which you write the name of the Class, e. g. `fix(StackClassloader): Some error description`.

#### Features

If your issue is regarding a feature request, a `feat:` prefix is necessary, followed by a short and concise description of your feature.

## Code Contributions

If you fork our repository and plan to contribute to our work yourself by handing in a pull request, you should keep the following in mind when contributing.

First of all if you plan to fix a bug, you should open issue first, declaring that you will fix the bug yourself and give an estimate of how long it will take.

### Conventional Commits

As you may have noticed we are using a special naming convention for our commits, called [Conventional Commits](conventionalcommits.org). If you plan to contribute, please use these for your commits, or we may won't add your feature as is to our repository.

### Bugs

If your Code is fixing a bug, please make sure to add a test to ensure that the bug is fixed once and for all. Please follow the naming scheme [below](#Tests).

### Tests

All of your code should be tested to ensure a production ready state at all times. Again, we have a special naming convention for our tests:

The function name should start with one of the following prefixes:

- `FUNCTION(#issue-no)` for reported bugs that require the test of a single function
- `INT(#issue-no)` for reported bugs that require an integration test
- `TEST(#issue-no)` all other tests with an assigned issue
- `FUNCTION` for tests of a single function
- `INT` for integration tests
- `TEST` for all other tests

Following those prefixes is a space after which the tested method is specified. If your test is an integration test, a short name of the feature tested should be used. 

The method should be specified as follows `methodName(paramter types) returnType`. If the one of the parameters or the return type is of a generic type, these should be added in a parentheses like so `Map(String, Int)`

This is followed by a -, which is followed by a lowercase description of the test.

An example for this would look like this:

```kotlin
FUNCTION(#10) foo(String?, Int) List(String) - null string
```

Furthermore all tests should be placed together in a class, if their method is originating from the same class. Integration test, however, should be placed in a separate class with their tested feature as the name.

### Pull Request

When opening a pull request please use the conventional commit scheme for your pull request title. This means a title for a feature should look like this `feat(branch-name): Description` and a title for a bug should look like this `fix(branch-name): Description`. Please note that if there is an issue number present in the name of the branch it should be omitted. 

Additionally if your feature/bug fix has an issue, please reference it at the bottom of the pull request, like so:`Closes #1`, so it will be closed upon merge.

All pull requests should merge into develop.