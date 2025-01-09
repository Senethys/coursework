#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
import codecs
import sys
from SearchPage import SearchPage
from ErrorPage import ErrorPage
''' Fetches all information from the first page "search" field and creates
a page "SearchPage" with the results'''

form = cgi.FieldStorage()
searchTerm=form.getfirst("searchterm","")

try:
    print 'Content-type: text/html\r\n'
    page=SearchPage("Search results")
    page.setSearchTerm(searchTerm)
    page.renderPage()

except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Error")
    page.renderPage()
