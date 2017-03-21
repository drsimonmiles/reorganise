package reorganise.client.components

import japgolly.scalajs.react.ReactNode
import japgolly.scalajs.react.vdom.prefix_<^._

/**
  * Provides type-safe access to Font Awesome icons
  */
object FAIcon {
  case class Icon (name: String) {
    def apply (): ReactNode = basic

    def banned: ReactNode =
      <.span (^.className := "fa-stack",
        <.i (^.className := s"fa fa-$name fa-stack-1x"),
        <.i (^.className := "fa fa-ban fa-stack-2x")
      )

    def basic: ReactNode =
      <.i (^.className := s"fa fa-$name", ^.key := s"fa fa-$name")

    def fixedWith: ReactNode =
      <.i (^.className := s"fa fa-$name fa-fw", ^.key := s"fa fa-$name fa-fw")
  }

  def adjust = Icon ("adjust")

  def adn = Icon ("adn")

  def alignCenter = Icon ("align-center")

  def alignJustify = Icon ("align-justify")

  def alignLeft = Icon ("align-left")

  def alignRight = Icon ("align-right")

  def ambulance = Icon ("ambulance")

  def anchor = Icon ("anchor")

  def android = Icon ("android")

  def angellist = Icon ("angellist")

  def angleDoubleDown = Icon ("angle-double-down")

  def angleDoubleLeft = Icon ("angle-double-left")

  def angleDoubleRight = Icon ("angle-double-right")

  def angleDoubleUp = Icon ("angle-double-up")

  def angleDown = Icon ("angle-down")

  def angleLeft = Icon ("angle-left")

  def angleRight = Icon ("angle-right")

  def angleUp = Icon ("angle-up")

  def apple = Icon ("apple")

  def archive = Icon ("archive")

  def areaChart = Icon ("area-chart")

  def arrowCircleDown = Icon ("arrow-circle-down")

  def arrowCircleLeft = Icon ("arrow-circle-left")

  def arrowCircleODown = Icon ("arrow-circle-o-down")

  def arrowCircleOLeft = Icon ("arrow-circle-o-left")

  def arrowCircleORight = Icon ("arrow-circle-o-right")

  def arrowCircleOUp = Icon ("arrow-circle-o-up")

  def arrowCircleRight = Icon ("arrow-circle-right")

  def arrowCircleUp = Icon ("arrow-circle-up")

  def arrowDown = Icon ("arrow-down")

  def arrowLeft = Icon ("arrow-left")

  def arrowRight = Icon ("arrow-right")

  def arrowUp = Icon ("arrow-up")

  def arrows = Icon ("arrows")

  def arrowsAlt = Icon ("arrows-alt")

  def arrowsH = Icon ("arrows-h")

  def arrowsV = Icon ("arrows-v")

  def asterisk = Icon ("asterisk")

  def at = Icon ("at")

  def automobile = Icon ("automobile")

  def backward = Icon ("backward")

  def ban = Icon ("ban")

  def bank = Icon ("bank")

  def barChart = Icon ("bar-chart")

  def barChartO = Icon ("bar-chart-o")

  def barcode = Icon ("barcode")

  def bars = Icon ("bars")

  def bed = Icon ("bed")

  def beer = Icon ("beer")

  def behance = Icon ("behance")

  def behanceSquare = Icon ("behance-square")

  def bell = Icon ("bell")

  def bellO = Icon ("bell-o")

  def bellSlash = Icon ("bell-slash")

  def bellSlashO = Icon ("bell-slash-o")

  def bicycle = Icon ("bicycle")

  def binoculars = Icon ("binoculars")

  def birthdayCake = Icon ("birthday-cake")

  def bitbucket = Icon ("bitbucket")

  def bitbucketSquare = Icon ("bitbucket-square")

  def bitcoin = Icon ("bitcoin")

  def bold = Icon ("bold")

  def bolt = Icon ("bolt")

  def bomb = Icon ("bomb")

  def book = Icon ("book")

  def bookmark = Icon ("bookmark")

  def bookmarkO = Icon ("bookmark-o")

  def briefcase = Icon ("briefcase")

  def btc = Icon ("btc")

  def bug = Icon ("bug")

  def building = Icon ("building")

  def buildingO = Icon ("building-o")

  def bullhorn = Icon ("bullhorn")

  def bullseye = Icon ("bullseye")

  def bus = Icon ("bus")

  def buysellads = Icon ("buysellads")

  def cab = Icon ("cab")

  def calculator = Icon ("calculator")

  def calendar = Icon ("calendar")

  def calendarO = Icon ("calendar-o")

  def camera = Icon ("camera")

  def cameraRetro = Icon ("camera-retro")

  def car = Icon ("car")

  def caretDown = Icon ("caret-down")

  def caretLeft = Icon ("caret-left")

  def caretRight = Icon ("caret-right")

  def caretSquareODown = Icon ("caret-square-o-down")

  def caretSquareOLeft = Icon ("caret-square-o-left")

  def caretSquareORight = Icon ("caret-square-o-right")

  def caretSquareOUp = Icon ("caret-square-o-up")

  def caretUp = Icon ("caret-up")

  def cartArrowDown = Icon ("cart-arrow-down")

  def cartPlus = Icon ("cart-plus")

  def cc = Icon ("cc")

  def ccAmex = Icon ("cc-amex")

  def ccDiscover = Icon ("cc-discover")

  def ccMastercard = Icon ("cc-mastercard")

  def ccPaypal = Icon ("cc-paypal")

  def ccStripe = Icon ("cc-stripe")

  def ccVisa = Icon ("cc-visa")

  def certificate = Icon ("certificate")

  def chain = Icon ("chain")

  def chainBroken = Icon ("chain-broken")

  def check = Icon ("check")

  def checkCircle = Icon ("check-circle")

  def checkCircleO = Icon ("check-circle-o")

  def checkSquare = Icon ("check-square")

  def checkSquareO = Icon ("check-square-o")

  def chevronCircleDown = Icon ("chevron-circle-down")

  def chevronCircleLeft = Icon ("chevron-circle-left")

  def chevronCircleRight = Icon ("chevron-circle-right")

  def chevronCircleUp = Icon ("chevron-circle-up")

  def chevronDown = Icon ("chevron-down")

  def chevronLeft = Icon ("chevron-left")

  def chevronRight = Icon ("chevron-right")

  def chevronUp = Icon ("chevron-up")

  def child = Icon ("child")

  def circle = Icon ("circle")

  def circleO = Icon ("circle-o")

  def circleONotch = Icon ("circle-o-notch")

  def circleThin = Icon ("circle-thin")

  def clipboard = Icon ("clipboard")

  def clockO = Icon ("clock-o")

  def close = Icon ("close")

  def cloud = Icon ("cloud")

  def cloudDownload = Icon ("cloud-download")

  def cloudUpload = Icon ("cloud-upload")

  def cny = Icon ("cny")

  def code = Icon ("code")

  def codeFork = Icon ("code-fork")

  def codepen = Icon ("codepen")

  def coffee = Icon ("coffee")

  def cog = Icon ("cog")

  def cogs = Icon ("cogs")

  def columns = Icon ("columns")

  def comment = Icon ("comment")

  def commentO = Icon ("comment-o")

  def comments = Icon ("comments")

  def commentsO = Icon ("comments-o")

  def compass = Icon ("compass")

  def compress = Icon ("compress")

  def connectdevelop = Icon ("connectdevelop")

  def copy = Icon ("copy")

  def copyright = Icon ("copyright")

  def creditCard = Icon ("credit-card")

  def crop = Icon ("crop")

  def crosshairs = Icon ("crosshairs")

  def css3 = Icon ("css3")

  def cube = Icon ("cube")

  def cubes = Icon ("cubes")

  def cut = Icon ("cut")

  def cutlery = Icon ("cutlery")

  def dashboard = Icon ("dashboard")

  def dashcube = Icon ("dashcube")

  def database = Icon ("database")

  def dedent = Icon ("dedent")

  def delicious = Icon ("delicious")

  def desktop = Icon ("desktop")

  def deviantart = Icon ("deviantart")

  def diamond = Icon ("diamond")

  def digg = Icon ("digg")

  def dollar = Icon ("dollar")

  def dotCircleO = Icon ("dot-circle-o")

  def download = Icon ("download")

  def dribbble = Icon ("dribbble")

  def dropbox = Icon ("dropbox")

  def drupal = Icon ("drupal")

  def edit = Icon ("edit")

  def eject = Icon ("eject")

  def ellipsisH = Icon ("ellipsis-h")

  def ellipsisV = Icon ("ellipsis-v")

  def empire = Icon ("empire")

  def envelope = Icon ("envelope")

  def envelopeO = Icon ("envelope-o")

  def envelopeSquare = Icon ("envelope-square")

  def eraser = Icon ("eraser")

  def eur = Icon ("eur")

  def euro = Icon ("euro")

  def exchange = Icon ("exchange")

  def exclamation = Icon ("exclamation")

  def exclamationCircle = Icon ("exclamation-circle")

  def exclamationTriangle = Icon ("exclamation-triangle")

  def expand = Icon ("expand")

  def externalLink = Icon ("external-link")

  def externalLinkSquare = Icon ("external-link-square")

  def eye = Icon ("eye")

  def eyeSlash = Icon ("eye-slash")

  def eyedropper = Icon ("eyedropper")

  def facebook = Icon ("facebook")

  def facebookF = Icon ("facebook-f")

  def facebookOfficial = Icon ("facebook-official")

  def facebookSquare = Icon ("facebook-square")

  def fastBackward = Icon ("fast-backward")

  def fastForward = Icon ("fast-forward")

  def fax = Icon ("fax")

  def female = Icon ("female")

  def fighterJet = Icon ("fighter-jet")

  def file = Icon ("file")

  def fileArchiveO = Icon ("file-archive-o")

  def fileAudioO = Icon ("file-audio-o")

  def fileCodeO = Icon ("file-code-o")

  def fileExcelO = Icon ("file-excel-o")

  def fileImageO = Icon ("file-image-o")

  def fileMovieO = Icon ("file-movie-o")

  def fileO = Icon ("file-o")

  def filePdfO = Icon ("file-pdf-o")

  def filePhotoO = Icon ("file-photo-o")

  def filePictureO = Icon ("file-picture-o")

  def filePowerpointO = Icon ("file-powerpoint-o")

  def fileSoundO = Icon ("file-sound-o")

  def fileText = Icon ("file-text")

  def fileTextO = Icon ("file-text-o")

  def fileVideoO = Icon ("file-video-o")

  def fileWordO = Icon ("file-word-o")

  def fileZipO = Icon ("file-zip-o")

  def filesO = Icon ("files-o")

  def film = Icon ("film")

  def filter = Icon ("filter")

  def fire = Icon ("fire")

  def fireExtinguisher = Icon ("fire-extinguisher")

  def flag = Icon ("flag")

  def flagCheckered = Icon ("flag-checkered")

  def flagO = Icon ("flag-o")

  def flash = Icon ("flash")

  def flask = Icon ("flask")

  def flickr = Icon ("flickr")

  def floppyO = Icon ("floppy-o")

  def folder = Icon ("folder")

  def folderO = Icon ("folder-o")

  def folderOpen = Icon ("folder-open")

  def folderOpenO = Icon ("folder-open-o")

  def font = Icon ("font")

  def forumbee = Icon ("forumbee")

  def forward = Icon ("forward")

  def foursquare = Icon ("foursquare")

  def frownO = Icon ("frown-o")

  def futbolO = Icon ("futbol-o")

  def gamepad = Icon ("gamepad")

  def gavel = Icon ("gavel")

  def gbp = Icon ("gbp")

  def ge = Icon ("ge")

  def gear = Icon ("gear")

  def gears = Icon ("gears")

  def genderless = Icon ("genderless")

  def gift = Icon ("gift")

  def git = Icon ("git")

  def gitSquare = Icon ("git-square")

  def github = Icon ("github")

  def githubAlt = Icon ("github-alt")

  def githubSquare = Icon ("github-square")

  def gittip = Icon ("gittip")

  def glass = Icon ("glass")

  def globe = Icon ("globe")

  def google = Icon ("google")

  def googlePlus = Icon ("google-plus")

  def googlePlusSquare = Icon ("google-plus-square")

  def googleWallet = Icon ("google-wallet")

  def graduationCap = Icon ("graduation-cap")

  def gratipay = Icon ("gratipay")

  def group = Icon ("group")

  def hSquare = Icon ("h-square")

  def hackerNews = Icon ("hacker-news")

  def handODown = Icon ("hand-o-down")

  def handOLeft = Icon ("hand-o-left")

  def handORight = Icon ("hand-o-right")

  def handOUp = Icon ("hand-o-up")

  def hddO = Icon ("hdd-o")

  def header = Icon ("header")

  def headphones = Icon ("headphones")

  def heart = Icon ("heart")

  def heartO = Icon ("heart-o")

  def heartbeat = Icon ("heartbeat")

  def history = Icon ("history")

  def home = Icon ("home")

  def hospitalO = Icon ("hospital-o")

  def hotel = Icon ("hotel")

  def html5 = Icon ("html5")

  def ils = Icon ("ils")

  def image = Icon ("image")

  def inbox = Icon ("inbox")

  def indent = Icon ("indent")

  def info = Icon ("info")

  def infoCircle = Icon ("info-circle")

  def inr = Icon ("inr")

  def instagram = Icon ("instagram")

  def institution = Icon ("institution")

  def ioxhost = Icon ("ioxhost")

  def italic = Icon ("italic")

  def joomla = Icon ("joomla")

  def jpy = Icon ("jpy")

  def jsfiddle = Icon ("jsfiddle")

  def key = Icon ("key")

  def keyboardO = Icon ("keyboard-o")

  def krw = Icon ("krw")

  def language = Icon ("language")

  def laptop = Icon ("laptop")

  def lastfm = Icon ("lastfm")

  def lastfmSquare = Icon ("lastfm-square")

  def leaf = Icon ("leaf")

  def leanpub = Icon ("leanpub")

  def legal = Icon ("legal")

  def lemonO = Icon ("lemon-o")

  def levelDown = Icon ("level-down")

  def levelUp = Icon ("level-up")

  def lifeBouy = Icon ("life-bouy")

  def lifeBuoy = Icon ("life-buoy")

  def lifeRing = Icon ("life-ring")

  def lifeSaver = Icon ("life-saver")

  def lightbulbO = Icon ("lightbulb-o")

  def lineChart = Icon ("line-chart")

  def link = Icon ("link")

  def linkedin = Icon ("linkedin")

  def linkedinSquare = Icon ("linkedin-square")

  def linux = Icon ("linux")

  def list = Icon ("list")

  def listAlt = Icon ("list-alt")

  def listOl = Icon ("list-ol")

  def listUl = Icon ("list-ul")

  def locationArrow = Icon ("location-arrow")

  def lock = Icon ("lock")

  def longArrowDown = Icon ("long-arrow-down")

  def longArrowLeft = Icon ("long-arrow-left")

  def longArrowRight = Icon ("long-arrow-right")

  def longArrowUp = Icon ("long-arrow-up")

  def magic = Icon ("magic")

  def magnet = Icon ("magnet")

  def mailForward = Icon ("mail-forward")

  def mailReply = Icon ("mail-reply")

  def mailReplyAll = Icon ("mail-reply-all")

  def male = Icon ("male")

  def mapMarker = Icon ("map-marker")

  def mars = Icon ("mars")

  def marsDouble = Icon ("mars-double")

  def marsStroke = Icon ("mars-stroke")

  def marsStrokeH = Icon ("mars-stroke-h")

  def marsStrokeV = Icon ("mars-stroke-v")

  def maxcdn = Icon ("maxcdn")

  def meanpath = Icon ("meanpath")

  def medium = Icon ("medium")

  def medkit = Icon ("medkit")

  def mehO = Icon ("meh-o")

  def mercury = Icon ("mercury")

  def microphone = Icon ("microphone")

  def microphoneSlash = Icon ("microphone-slash")

  def minus = Icon ("minus")

  def minusCircle = Icon ("minus-circle")

  def minusSquare = Icon ("minus-square")

  def minusSquareO = Icon ("minus-square-o")

  def mobile = Icon ("mobile")

  def mobilePhone = Icon ("mobile-phone")

  def money = Icon ("money")

  def moonO = Icon ("moon-o")

  def mortarBoard = Icon ("mortar-board")

  def motorcycle = Icon ("motorcycle")

  def music = Icon ("music")

  def navicon = Icon ("navicon")

  def neuter = Icon ("neuter")

  def newspaperO = Icon ("newspaper-o")

  def openid = Icon ("openid")

  def outdent = Icon ("outdent")

  def pagelines = Icon ("pagelines")

  def paintBrush = Icon ("paint-brush")

  def paperPlane = Icon ("paper-plane")

  def paperPlaneO = Icon ("paper-plane-o")

  def paperclip = Icon ("paperclip")

  def paragraph = Icon ("paragraph")

  def paste = Icon ("paste")

  def pause = Icon ("pause")

  def paw = Icon ("paw")

  def paypal = Icon ("paypal")

  def pencil = Icon ("pencil")

  def pencilSquare = Icon ("pencil-square")

  def pencilSquareO = Icon ("pencil-square-o")

  def phone = Icon ("phone")

  def phoneSquare = Icon ("phone-square")

  def photo = Icon ("photo")

  def pictureO = Icon ("picture-o")

  def pieChart = Icon ("pie-chart")

  def piedPiper = Icon ("pied-piper")

  def piedPiperAlt = Icon ("pied-piper-alt")

  def pinterest = Icon ("pinterest")

  def pinterestP = Icon ("pinterest-p")

  def pinterestSquare = Icon ("pinterest-square")

  def plane = Icon ("plane")

  def play = Icon ("play")

  def playCircle = Icon ("play-circle")

  def playCircleO = Icon ("play-circle-o")

  def plug = Icon ("plug")

  def plus = Icon ("plus")

  def plusCircle = Icon ("plus-circle")

  def plusSquare = Icon ("plus-square")

  def plusSquareO = Icon ("plus-square-o")

  def powerOff = Icon ("power-off")

  def print = Icon ("print")

  def puzzlePiece = Icon ("puzzle-piece")

  def qq = Icon ("qq")

  def qrcode = Icon ("qrcode")

  def question = Icon ("question")

  def questionCircle = Icon ("question-circle")

  def quoteLeft = Icon ("quote-left")

  def quoteRight = Icon ("quote-right")

  def ra = Icon ("ra")

  def random = Icon ("random")

  def rebel = Icon ("rebel")

  def recycle = Icon ("recycle")

  def reddit = Icon ("reddit")

  def redditSquare = Icon ("reddit-square")

  def refresh = Icon ("refresh")

  def remove = Icon ("remove")

  def renren = Icon ("renren")

  def reorder = Icon ("reorder")

  def repeat = Icon ("repeat")

  def reply = Icon ("reply")

  def replyAll = Icon ("reply-all")

  def retweet = Icon ("retweet")

  def rmb = Icon ("rmb")

  def road = Icon ("road")

  def rocket = Icon ("rocket")

  def rotateLeft = Icon ("rotate-left")

  def rotateRight = Icon ("rotate-right")

  def rouble = Icon ("rouble")

  def rss = Icon ("rss")

  def rssSquare = Icon ("rss-square")

  def rub = Icon ("rub")

  def ruble = Icon ("ruble")

  def rupee = Icon ("rupee")

  def save = Icon ("save")

  def scissors = Icon ("scissors")

  def search = Icon ("search")

  def searchMinus = Icon ("search-minus")

  def searchPlus = Icon ("search-plus")

  def sellsy = Icon ("sellsy")

  def send = Icon ("send")

  def sendO = Icon ("send-o")

  def server = Icon ("server")

  def share = Icon ("share")

  def shareAlt = Icon ("share-alt")

  def shareAltSquare = Icon ("share-alt-square")

  def shareSquare = Icon ("share-square")

  def shareSquareO = Icon ("share-square-o")

  def shekel = Icon ("shekel")

  def sheqel = Icon ("sheqel")

  def shield = Icon ("shield")

  def ship = Icon ("ship")

  def shirtsinbulk = Icon ("shirtsinbulk")

  def shoppingCart = Icon ("shopping-cart")

  def signIn = Icon ("sign-in")

  def signOut = Icon ("sign-out")

  def signal = Icon ("signal")

  def simplybuilt = Icon ("simplybuilt")

  def sitemap = Icon ("sitemap")

  def skyatlas = Icon ("skyatlas")

  def skype = Icon ("skype")

  def slack = Icon ("slack")

  def sliders = Icon ("sliders")

  def slideshare = Icon ("slideshare")

  def smileO = Icon ("smile-o")

  def soccerBallO = Icon ("soccer-ball-o")

  def sort = Icon ("sort")

  def sortAlphaAsc = Icon ("sort-alpha-asc")

  def sortAlphaDesc = Icon ("sort-alpha-desc")

  def sortAmountAsc = Icon ("sort-amount-asc")

  def sortAmountDesc = Icon ("sort-amount-desc")

  def sortAsc = Icon ("sort-asc")

  def sortDesc = Icon ("sort-desc")

  def sortDown = Icon ("sort-down")

  def sortNumericAsc = Icon ("sort-numeric-asc")

  def sortNumericDesc = Icon ("sort-numeric-desc")

  def sortUp = Icon ("sort-up")

  def soundcloud = Icon ("soundcloud")

  def spaceShuttle = Icon ("space-shuttle")

  def spinner = Icon ("spinner")

  def spoon = Icon ("spoon")

  def spotify = Icon ("spotify")

  def square = Icon ("square")

  def squareO = Icon ("square-o")

  def stackExchange = Icon ("stack-exchange")

  def stackOverflow = Icon ("stack-overflow")

  def star = Icon ("star")

  def starHalf = Icon ("star-half")

  def starHalfEmpty = Icon ("star-half-empty")

  def starHalfFull = Icon ("star-half-full")

  def starHalfO = Icon ("star-half-o")

  def starO = Icon ("star-o")

  def steam = Icon ("steam")

  def steamSquare = Icon ("steam-square")

  def stepBackward = Icon ("step-backward")

  def stepForward = Icon ("step-forward")

  def stethoscope = Icon ("stethoscope")

  def stop = Icon ("stop")

  def streetView = Icon ("street-view")

  def strikethrough = Icon ("strikethrough")

  def stumbleupon = Icon ("stumbleupon")

  def stumbleuponCircle = Icon ("stumbleupon-circle")

  def subscript = Icon ("subscript")

  def subway = Icon ("subway")

  def suitcase = Icon ("suitcase")

  def sunO = Icon ("sun-o")

  def superscript = Icon ("superscript")

  def support = Icon ("support")

  def table = Icon ("table")

  def tablet = Icon ("tablet")

  def tachometer = Icon ("tachometer")

  def tag = Icon ("tag")

  def tags = Icon ("tags")

  def tasks = Icon ("tasks")

  def taxi = Icon ("taxi")

  def tencentWeibo = Icon ("tencent-weibo")

  def terminal = Icon ("terminal")

  def textHeight = Icon ("text-height")

  def textWidth = Icon ("text-width")

  def th = Icon ("th")

  def thLarge = Icon ("th-large")

  def thList = Icon ("th-list")

  def thumbTack = Icon ("thumb-tack")

  def thumbsDown = Icon ("thumbs-down")

  def thumbsODown = Icon ("thumbs-o-down")

  def thumbsOUp = Icon ("thumbs-o-up")

  def thumbsUp = Icon ("thumbs-up")

  def ticket = Icon ("ticket")

  def times = Icon ("times")

  def timesCircle = Icon ("times-circle")

  def timesCircleO = Icon ("times-circle-o")

  def tint = Icon ("tint")

  def toggleDown = Icon ("toggle-down")

  def toggleLeft = Icon ("toggle-left")

  def toggleOff = Icon ("toggle-off")

  def toggleOn = Icon ("toggle-on")

  def toggleRight = Icon ("toggle-right")

  def toggleUp = Icon ("toggle-up")

  def train = Icon ("train")

  def transgender = Icon ("transgender")

  def transgenderAlt = Icon ("transgender-alt")

  def trash = Icon ("trash")

  def trashO = Icon ("trash-o")

  def tree = Icon ("tree")

  def trello = Icon ("trello")

  def trophy = Icon ("trophy")

  def truck = Icon ("truck")

  def `try` = Icon ("try")

  def tty = Icon ("tty")

  def tumblr = Icon ("tumblr")

  def tumblrSquare = Icon ("tumblr-square")

  def turkishLira = Icon ("turkish-lira")

  def twitch = Icon ("twitch")

  def twitter = Icon ("twitter")

  def twitterSquare = Icon ("twitter-square")

  def umbrella = Icon ("umbrella")

  def underline = Icon ("underline")

  def undo = Icon ("undo")

  def university = Icon ("university")

  def unlink = Icon ("unlink")

  def unlock = Icon ("unlock")

  def unlockAlt = Icon ("unlock-alt")

  def unsorted = Icon ("unsorted")

  def upload = Icon ("upload")

  def usd = Icon ("usd")

  def user = Icon ("user")

  def userMd = Icon ("user-md")

  def userPlus = Icon ("user-plus")

  def userSecret = Icon ("user-secret")

  def userTimes = Icon ("user-times")

  def users = Icon ("users")

  def venus = Icon ("venus")

  def venusDouble = Icon ("venus-double")

  def venusMars = Icon ("venus-mars")

  def viacoin = Icon ("viacoin")

  def videoCamera = Icon ("video-camera")

  def vimeoSquare = Icon ("vimeo-square")

  def vine = Icon ("vine")

  def vk = Icon ("vk")

  def volumeDown = Icon ("volume-down")

  def volumeOff = Icon ("volume-off")

  def volumeUp = Icon ("volume-up")

  def warning = Icon ("warning")

  def wechat = Icon ("wechat")

  def weibo = Icon ("weibo")

  def weixin = Icon ("weixin")

  def whatsapp = Icon ("whatsapp")

  def wheelchair = Icon ("wheelchair")

  def wifi = Icon ("wifi")

  def windows = Icon ("windows")

  def won = Icon ("won")

  def wordpress = Icon ("wordpress")

  def wrench = Icon ("wrench")

  def xing = Icon ("xing")

  def xingSquare = Icon ("xing-square")

  def yahoo = Icon ("yahoo")

  def yelp = Icon ("yelp")

  def yen = Icon ("yen")

  def youtube = Icon ("youtube")

  def youtubePlay = Icon ("youtube-play")

  def youtubeSquare = Icon ("youtube-square")
}

