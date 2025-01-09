#!/usr/local/bin/python
# -*- coding: latin-1 -*-
from AddNote import AddNote
import cgitb
cgitb.enable()
import cgi
from ErrorPage import ErrorPage
from PersonReg import PersonReg
form = cgi.FieldStorage()
agent_id = form.getfirst("id")
'''Prints add note page to relevant agent using its id'''
try:
    print 'Content-type: text/html\r\n'
    page = AddNote("Detail view")
    page.setAgent(agent_id)
    page.renderPage()
except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Invalid data")
    page.renderPage()
