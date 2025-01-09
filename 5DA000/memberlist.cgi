#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from MemberList import MemberList
from ErrorPage import ErrorPage
'''Creates a list of all the members in the database with the help of an auto
empty search form.
CGI file called when user presses "Memberlist" from the first page'''

form = cgi.FieldStorage()

try:
    searchTerm=form.getfirst("searchTerm")
    print 'Content-type: text/html\r\n'
    page=MemberList("List")
    page.renderPage()
except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Invalid data")
    page.renderPage()
