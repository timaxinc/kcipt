# Contributing

We would love, if you plan to contribute to this project in any way.
However there are a few things you should consider before opening an issue or submitting a pull request.
We know we have some conventions that may seem unusual to you, but they help us to ensure a high code quality and allow us to react to issues more efficiently.
Don't be scared by the extent of our policies, we can assure you they seem more complex than they really are.

## Issues

If you find a bug or simply have a feature request, let us know with an issue.
Before submitting your issue please check that it complies with the following requirements.

### Templates

First of all, please use our templates.
The different sections were chosen after careful consideration and are especially crucial, if you are reporting a bug, as the information we ask you to provide could possibly play a major role in our debugging process.

### Naming Convention

Furthermore we have a special naming scheme for our issue titles that helps us assigning certain issues to team members, hence allowing us to process issues more quickly.

#### Bugs

If your issue is a bug report, the title should start with `fix: ` followed by a concise and short description of the bug you have encountered.
If the bug is related to a specific class, you can add a parentheses after the `fix`, in which you write the name of the Class, e. g. `fix(StackClassloader): Some error description`.

#### Features

If your issue is regarding a feature request, use the `feat:` prefix, followed by a short and concise description of your feature.

## Code Contributions

If plan to contribute to our work, you should keep the following in mind.

### Conventional Commits

As you may have noticed we are using a special naming convention for our commits, called [Conventional Commits](conventionalcommits.org).
If you plan to contribute, please use these for your commits, or we may won't add your feature as is to our repository.

### Bugs

If your Code is fixing a bug, please make sure to add a test to ensure that the bug is fixed once and for all.
Please follow the naming scheme [below](#Tests).

### Tests

All of your code should be tested to ensure a production ready state at all times. Again, we have a special naming convention for our tests:

The function name should start with one of the following prefixes:

- `FUNCTION(#issue-no)` for reported bugs that require the test of a single function
- `INT(#issue-no)` for reported bugs that require an integration test
- `TEST(#issue-no)` all other tests with an assigned issue
- `FUNCTION` for tests of a single function
- `INT` for integration tests
- `TEST` for all other tests

Following those prefixes is a space after which the tested method is specified.
If your test is an integration test, a short name of the feature tested should be used. 

The method should be specified as follows `methodName(paramter types) returnType`.
If the one of the parameters or the return type is of a generic type, these should be added in a parentheses like so `Map(String, Int)`

This is followed by a -, which is followed by a lowercase description of the test.
An exception to this are proper nouns or function names.

An example for this would look like this:

```kotlin
FUNCTION(#10) foo(String?, Int) List(String) - null string
```

Furthermore all tests should be placed together in a class, if their method is originating from the same class, so all methods from class A should be tested in ATest.
Integration tests, however, should be placed in a separate class with the tested feature as the name.

### Branches

Every branch should have an issue describing what is being done in that branch.
The title of the branch should start with the issue number followed by a `-`.
Furthermore all spaces should be representented by an additional `-`, however we strongly encourage you to keep the branch name short, at best just one word.

### Pull Requests

When opening a pull request please use the conventional commit scheme for your pull request title, meaning a title for a feature should look like this `feat(branch-name): Description` and a title for a bug should look like this `fix(branch-name): Description`.
Note that the number of the issue found in the name of the branch should be omitted. 

Additionally mention the issue prefixed by the `Closes` keyword in the footer, so that upon merge the corresponding issue is automatically closed.

All pull requests should merge into develop.

#### Reviewing

If you were assigned to review a Pull Request, please ensure that the following criteria are met:

- [ ] Every exported method is documented and no tags (e. g. `@param`) are missing
- [ ] All code complies with the `.editorconfig` found in project root
- [ ] The code is written in an easy to read manner
- [ ] The code is easy to maintain
- [ ] Every file ends with a blank line
- [ ] If the file contains a class, the name of the file should match it

If you find anything else that should be changed, don't hesitate to comment it.
