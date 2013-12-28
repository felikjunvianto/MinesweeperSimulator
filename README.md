MinesweeperSimulator
====================

Minesweeper simulator for simulating AI (used in Pekan Ristek Fasilkom UI 2013)

How to run:
<ol>
<li>Extract the package.</li>
<li>Open terminal or command line.</li>
<li>Go to the extracted folder, then type: "java MinesweeperSimulator"
</li>
</ol>

There are several field in the frame:
<ul>
<li>Width/height: to customize the width and height of the minefield.</li>
<li>Mines: to customize the number of mine in the minefield (at least 1, at most all of the cell minus 10).</li>
<li>Seed: to customize the random seed. Useful to simulate the same testcase multiple times. When it equals to -1, the seed is randomized.</li>
<li>Interval: to change the time interval to invoke the "click" in the minefield (at least 100).</li>
<li>Start button: to start the simulation</li>
</ul>

FAQ:
<ul>
<li><b>How does it work?</b> The basic priciple is to turn whole minefield into a bunch of linear equation, and then excecute elemination to fild whether a cell is mine or not.</li>
<li><b>Why does sometimes it fails?</b> Sometimes a cell can't be determined whether it is mine or not. If this happen, the program will choose randomly. Of course, if you are minesweeper-player, you will face this kind of situation ;)</li>
<li><b>When it fails, what does the crossed mine means?</b> It shows the last location where the program attempted to open, but unfortunately it is a mine. </li>
<li><b>can I use this for something?</b> Of course, you may use it for anything but commercial usage.</li>
</ul>

Contributor:
<ul>
<li>Adhitiya Murda Nugraha</li>
<li>Febriana Wulan Sari</li>
<li>Felik Junvianto</li>
<li>Gede Wahyu Adi Pramana</li>
<li>Irfan Nur Afif</li>
<li>William Gozali</li>
</ul>
