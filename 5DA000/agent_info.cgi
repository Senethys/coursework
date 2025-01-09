#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from AgentInfo import AgentInfo
from ErrorPage import ErrorPage
form = cgi.FieldStorage()

agent_id = form.getfirst("agentid", "1")

try:
        print 'Content-type: text/html\r\n'
        page = AgentInfo("Detail view")
        page.setAgent(agent_id)
        page.renderPage()
except:
        print 'Content-type: text/html\r\n'
        page = ErrorPage("Invalid data")
        page.renderPage()