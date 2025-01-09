#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from EditNote import EditNote
from ErrorPage import ErrorPage
form = cgi.FieldStorage()
note_id = form.getfirst("note_id")
agent_id = form.getfirst("agent_id")

try:
    print 'Content-type: text/html\r\n'
    page = EditNote("Edit Note")
    page.setNote(note_id)
    page.setAgent(agent_id)
    page.renderPage()
except:
    print 'Content-type: text/html\r\n'
    page = ErrorPage("Invalid data")
    page.renderPage()
