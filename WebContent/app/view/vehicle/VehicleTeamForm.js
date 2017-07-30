/**
 * 修改 车队 弹窗
 */
Ext.define('ivp.view.vehicle.VehicleTeamForm', {
    extend : 'Ext.window.Window',
    alias : 'widget.vehicleteamform',
    itemId : 'vehicleteamformId',

    // title : '修改车队',
    modal : true,
    constrainHeader : true,

    // autoShow : true,
    height : 400,
    width : 500,
    layout : 'fit',

    initComponent : function() {
        var me = this;
        var changingImage = {};
        if (me.udt == true) {
//            console.log('====================== me.logoPath', me.logoPath);
            changingImage = Ext.create('Ext.Img', {
                src : me.logoPath,
                width : 200,
                height : 200
            });
        }

        Ext.applyIf(me, {
            items : [{
                xtype : 'form',
                bodyPadding : 10,
                items : [{
                    xtype : 'textfield',
                    // allowBlank : false,
                    hidden : true,
                    name : 'id'

                }, {
                    xtype : 'textfield',
                    allowBlank : false,
                    fieldLabel : '车队名称',
                    emptyText : '例如：秋明山车神',
                    name : 'teamName'

                }, {
                    xtype : 'textfield',
                    emptyText : '例如：AE86',
                    fieldLabel : '车队编号',
                    name : 'teamCode'
                }, {
                    xtype : 'filefield',
                    anchor : '90%',
                    allowBlank : false,
                    name : 'logoFile',
                    fieldLabel : '<font color="red">*</font>LOGO',
                    buttonText : '选择文件',
                    clearOnSubmit : false,
                    validator : function(val) {
                        var fileName = /\.(jpg)$|\.(JPG)|\.(png)$|\.(PNG)$/;
                        if (!fileName.test(val) && val != "" && val != "格式不正确") {
                            return "请选择JPG图片或PNG格式文件";
                        }
                        return true;
                    }
                }, changingImage],
                buttons : [{
                    xtype : 'button',
                    text : '保存',
                    listeners : {
                        click : {
                            fn : me.onSubmit,
                            scope : me
                        }
                    }

                }, {
                    xtype : 'button',
                    text : '关闭',
                    listeners : {
                        click : {
                            fn : me.onCancelButtonClick,
                            scope : me
                        }
                    }

                }]
            }]
        });

        me.callParent(arguments);
    },

    // 关闭按钮
    onCancelButtonClick : function(btn) {
        btn.up('window').close();
    },

    /**
     * 提交
     */
    onSubmit : function(btn, e, eOpts) {
        // 这个是记住 loginview 窗口
        var view = this;
        // 先找到 form这个控件, 从btn 控件开始向上查找
        var formCmp = btn.up('form');
        var form = formCmp.getForm();
        if (form.isValid()) {

        } else {
            Ext.MessageBox.alert('', '您好像没填写车队名称');
            return false;
        }

        var values = form.getValues();
        // 如果有id 说明是修改
        // 如果没有，那么说明是 新建
        var submitURL = 's/vt/udt';
        if (values.id) {
            // submitURL = 's/r/udt';
        } else {
            submitURL = 's/vt/add'
        }

        form.submit({
            clientValidation : true,
            url : submitURL,
            waitMsg : "正在保存，请稍后...",
            success : function(form, action) {
                // /console.log('=========== success action ', action);
                Ext.Msg.alert('成功', action.result.message);

                view.close();
                view.gridStore.load();

            },
            failure : function(form, action) {
                // console.log('===========action ', action);
                Ext.Msg.alert('失败', action.result.message);
            }
        });
    }
});
