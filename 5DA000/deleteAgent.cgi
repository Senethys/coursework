#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from MemberList import MemberList
from ErrorPage import ErrorPage
from PersonReg import PersonReg
'''A smaller version of "saveChanges.cgi". Deletes one or many agents depending
on from which page (form) it is cgi is called.'''


register = PersonReg("database.db")
form = cgi.FieldStorage()
button_type = form.getfirst("button")
try:
    if button_type == "2":
        singleAgent = form.getfirst('id')
        register.deleteAgent(singleAgent)
    else:
        checkboxvals = form.getlist('checkid')
        register.deleteAgents(checkboxvals)

    print 'Content-type: text/html\r\n'
    page = MemberList("Member List")
    page.renderPage()

except:
        print 'Content-type: text/html\r\n'
        page = ErrorPage("Invalid data")
        page.renderPage()
