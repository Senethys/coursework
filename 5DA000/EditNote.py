# -*- coding: latin-1 -*-
from PersonReg import PersonReg
from WebPage import WebPage
import cgitb
cgitb.enable()


class EditNote(WebPage):

        def printBody(self):
                '''
                Page that lets edit old notes. All data are fetched from the database and
                displayed in the fields, ready to be edited
                '''
                print "<div class = 'AgentInfo'>"
                print "<h2> %s, %s </h2>" % (self.agent.getName(), self.agent.getLastname())
                print "<p>Email: %s </p>" % (self.agent.getEmail())
                print "</div>"
                print "<form action='saveChanges.cgi?note_id=%d&agent_id=%s' method='post'>" % (self.note[2], self.agent.getId())
                print "<input class='notetitle' type = 'text' value = '%s' name = 'note_title'>" % self.note[0]
                print "<textarea rows='15' cols='120' class='notetext' name = 'note_text'>%s</textarea>" % self.note[1]
                print "<input type='hidden' name ='button' value='3'>"
                print "<input class='notebutton' type='submit' value='Save Changes'> </form>"

                print "<form class = 'DeleteNotes' action = 'saveChanges.cgi?note_id=%d&agent_id=%s' method = 'post'>" % (self.note[2], self.agent.getId())
                print "<input type = 'hidden' name = 'button' value = '4'>"
                print "<input type = 'submit' value = 'Delete Note'> </form>"

                #Back button code from http://www.computerhope.com/issues/ch000317.htm
                print """<form class = ' BackButtonMemberList'><input type="button"
                value="Back" onClick="history.go(-1);return true;"></form>"""

        def setNote(self, note_id):
                '''
                str -> (title, note, note_id)
                Uses note id to get a note object, created by PersonReg after
                cursor gets information from the database.
                '''
                self.db = PersonReg("database.db")
                self.note = self.db.getNote(note_id)

        def setAgent(self, agent_id):
                '''Fetches info from SQL database using PersonReg and returns
                agent object.'''
                self.registry = PersonReg("database.db")
                self.agent = self.registry.getAgent(agent_id)
