    var tab = null;
    var accordion = null;
    var tree = null;
    $(function () {
        //ҳ�沼��
        $("#global_layout").ligerLayout({ leftWidth: 180, height: '100%', topHeight: 65, bottomHeight: 24, allowTopResize: false, allowBottomResize: false, allowLeftCollapse: true, onHeightChanged: f_heightChanged });
 
        var height = $(".l-layout-center").height();
 
        //Tab
        $("#framecenter").ligerTab({ height: height });
 
        //��ߵ������
        $("#global_left_nav").ligerAccordion({ height: height - 25, speed: null });
 
        $(".l-link").hover(function () {
            $(this).addClass("l-link-over");
        }, function () {
            $(this).removeClass("l-link-over");
        });
 
        //����Ƶ���˵�
        $("#global_channel_tree").ligerTree({
            url: '',
            checkbox: false,
            nodeWidth: 112,
            //attribute: ['nodename', 'url'],
            onSelect: function (node) {
                if (!node.data.url) return;
                var tabid = $(node.target).attr("tabid");
                if (!tabid) {
                    tabid = new Date().getTime();
                    $(node.target).attr("tabid", tabid)
                }
                f_addTab(tabid, node.data.text, node.data.url);
            }
        });
 
        //��ݲ˵�
        var menu = $.ligerMenu({ width: 120, items:
		[
			{ text: '������ҳ', click: itemclick },
			{ text: '�޸�����', click: itemclick },
			{ line: true },
			{ text: '�رղ˵�', click: itemclick }
		]
        });
        $("#tab-tools-nav").bind("click", function () {
            var offset = $(this).offset(); //ȡ���¼������λ��
            menu.show({ top: offset.top + 27, left: offset.left - 120 });
            return false;
        });
 
        tab = $("#framecenter").ligerGetTabManager();
        accordion = $("#global_left_nav").ligerGetAccordionManager();
        tree = $("#global_channel_tree").ligerGetTreeManager();
        //tree.expandAll(); //Ĭ��չ�����нڵ�
        $("#pageloading_bg,#pageloading").hide();
    });
 
    //��ݲ˵��ص�����
    function itemclick(item) {
        switch (item.text) {
            case "������ҳ":
                f_addTab('home', '��������', 'center.html');
                break;
            case "��ݵ���":
                //���ú���
                break;
            case "�޸�����":
                f_addTab('manager_pwd', '�޸�����', '####');
                break;
            default:
                //�رմ���
                break;
        }
    }
    function f_heightChanged(options) {
        if (tab)
            tab.addHeight(options.diff);
        if (accordion && options.middleHeight - 24 > 0)
            accordion.setHeight(options.middleHeight - 24);
    }
    //���Tab���ɴ�3������
    function f_addTab(tabid, text, url, iconcss) {
        if (arguments.length == 4) {
            tab.addTabItem({ tabid: tabid, text: text, url: url, iconcss: iconcss });
        } else {
            tab.addTabItem({ tabid: tabid, text: text, url: url });
        }
    }
    //��ʾDialog���ر�Tab
    function f_errorTab(tit, msg) {
        $.ligerDialog.open({
            isDrag: false,
            allowClose: false,
            type: 'error',
            title: tit,
            content: msg,
            buttons: [{
                text: 'ȷ��',
                onclick: function (item, dialog, index) {
                    //���ҵ�ǰiframe����
                    var itemiframe = "#framecenter .l-tab-content .l-tab-content-item";
                    var curriframe = "";
                    $(itemiframe).each(function () {
                        if ($(this).css("display") != "none") {
                            curriframe = $(this).attr("tabid");
                            return false;
                        }
                    });
                    if (curriframe != "") {
                        tab.removeTabItem(curriframe);
                        dialog.close();
                    }
                }
            }]
        });
    }