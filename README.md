# Markdown to HTML Converter
A program that converts a small subset of markdown to HTML


## Formatting Specifics
| Markdown                               | HTML                                              |
| -------------------------------------- | ------------------------------------------------- |
| `# Heading 1`                          | `<h1>Heading 1</h1>`                              | 
| `## Heading 2`                         | `<h2>Heading 2</h2>`                              | 
| `...`                                  | `...`                                             | 
| `###### Heading 6`                     | `<h6>Heading 6</h6>`                              | 
| `Unformatted text`                     | `<p>Unformatted text</p>`                         | 
| `[Link text](https://www.example.com)` | `<a href="https://www.example.com">Link text</a>` | 
| `Blank line`                           | `Ignored`                                         | 



## Sample tests

```
# Sample Document

Hello!

This is a sample markdown for the [Mailchimp](https://www.mailchimp.com) homework assignment.
```


We would expect this to convert to the following HTML:

```
<h1>Sample Document</h1>

<p>Hello!</p>

<p>This is a sample markdown for the <a href="https://www.mailchimp.com">Mailchimp</a> homework assignment.</p>
```


Similarly, this sample:

```
# Header one

Hello there

How are you?
What's going on?

## Another Header

This is a paragraph [with an inline link](http://google.com). Neat, eh?

## This is a header [with a link](http://yahoo.com)
```

Would convert to the following HTML:

```
<h1>Header one</h1>

<p>Hello there</p>

<p>How are you?</p>

<p>What's going on?</p>

<h2>Another Header</h2>

<p>This is a paragraph <a href="http://google.com">with an inline link</a>. Neat, eh?</p>

<h2>This is a header <a href="http://yahoo.com">with a link</a></h2>
```

## System Design Document
For a detailed system design document, please refer to `Markdown to HTML Converter System Design Guide.pdf` in the project directory.
