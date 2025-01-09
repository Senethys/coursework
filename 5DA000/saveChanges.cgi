#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from AgentInfo import AgentInfo
from ErrorPage import ErrorPage
from PersonReg import PersonReg
''' Handles most changes in the database by getting a hidden value from "button"
which is present in most forms. Depending on a value (a string number) relevant
database command is executed with the help of PersonReg.'''

db = PersonReg("database.db")
form = cgi.FieldStorage()
button_type = form.getfirst('button')

try:
        if '1' in button_type:
                agent_id = int(form.getfirst('id'))
                title = form.getfirst("note_title", "This field was left empty.")
                note = form.getfirst("note_text", "This field was left empty.")
                db.addNotes(int(agent_id), title, note)
        elif '3' in button_type:
                note_id = int(form.getfirst("note_id", 0))
                agent_id = int(form.getfirst("agent_id", 0))
                title = form.getfirst("note_title", "This field was left empty.")
                note = form.getfirst("note_text", "This field was left empty.")
                db.updateNote(note_id, title, note)
        elif '4' in button_type:
                note_id = int(form.getfirst("note_id", 0))
                agent_id = int(form.getfirst("agent_id", 0))
                db.deleteNote(note_id)
        elif '5' in button_type:
                agent_id = int(form.getfirst("agent_id", 0))
                db.deleteNotes(agent_id)
        else:
                agent_id = form.getfirst('id')
                fname = form.getfirst("fname","")
                lname = form.getfirst("lname","")
                mail = form.getfirst("mail", "")
                db.updateAgent(fname, lname, mail, agent_id)

        print 'Content-type: text/html\r\n'
        page = AgentInfo("Details")
        page.setAgent(agent_id)
        page.renderPage()

except:
        print 'Content-type: text/html\r\n'
        page = ErrorPage("Invalid data")
        page.renderPage()
