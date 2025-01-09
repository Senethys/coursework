#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from EditAgent import EditAgent
from ErrorPage import ErrorPage
form = cgi.FieldStorage()
agent_id = form.getfirst('id')

try:
    print 'Content-type: text/html\r\n'
    page = EditAgent("Edit Member")
    page.setAgent(agent_id)
    page.renderPage()
except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Invalid data")
    page.renderPage()
