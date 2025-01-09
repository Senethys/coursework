#!/usr/local/bin/python
# -*- coding: latin-1 -*-
import cgitb
cgitb.enable()
import cgi
from MemberList import MemberList
from PersonReg import PersonReg
from ErrorPage import ErrorPage
form = cgi.FieldStorage()
'''Sends entered data from forms present in the Memberlist page to PersonReg.'''


try:
    name=form.getfirst("fname","")
    lastname = form.getfirst("lname","")
    email = form.getfirst("mail", "")

    agent_notes = form.getfirst("note_text","This field was left empty.")
    agent_note_title = form.getfirst("note_title","This field was left empty.")

    db = PersonReg("database.db")
