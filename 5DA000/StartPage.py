# -*- coding: latin-1 -*-
from WebPage import WebPage
import cgitb
cgitb.enable()

class StartPage(WebPage):
        def printBody(self):
                '''
                First page initiated/printed from the index.cgi file, prints out relevant
                information. Includes a search field and a memberlist button that lets
                view all the members in the database.
                '''
                print """<div class = "StartPageForm">
                <form name="search" action="search.cgi" method="get">
                <input type="text" name="searchterm" />
                <input type="submit" name="submit" value="Search"/>
                </form>
                <form name="search" action="memberlist.cgi" method="get">
                <input type="submit" name="submit" value="Memberlist"/>
                </form>
                </div>
                """

                print """<p class= "introtext"> Welcome! Here, you can add people,
                their emails
                and notes about them. You can search for a member in the search field.
                If you leave the field empty, all members will be displayed.
                The search is conducted by name, lastname and email.
                Click on Memberlist if you want to see all members and edit them.</p>
                """
