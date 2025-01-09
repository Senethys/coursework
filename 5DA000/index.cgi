#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from StartPage import StartPage
from ErrorPage import ErrorPage
'''The first sent file to the client, initiates WebPage and StartPage
instance with relevant information'''


try:
    print 'Content-type: text/html\r\n'
    page=StartPage("The Address Book")
    page.renderPage()
except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Invalid data")
    page.renderPage()
