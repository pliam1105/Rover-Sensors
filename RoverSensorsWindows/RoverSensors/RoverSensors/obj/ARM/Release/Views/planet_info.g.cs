﻿#pragma checksum "C:\Users\pliam\OneDrive\Desktop\FLL 2019 ROVER\RoverSensorsWindows\RoverSensors\RoverSensors\Views\planet_info.xaml" "{8829d00f-11b8-4213-878b-770e8597ac16}" "18BA6A17C34D65B5DF87F78DFC371FE4F775787E21204DF46AD99578FDDC77EF"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RoverSensors.Views
{
    partial class planet_info : 
        global::Windows.UI.Xaml.Controls.Page, 
        global::Windows.UI.Xaml.Markup.IComponentConnector,
        global::Windows.UI.Xaml.Markup.IComponentConnector2
    {
        /// <summary>
        /// Connect()
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 0.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void Connect(int connectionId, object target)
        {
            switch(connectionId)
            {
            case 1: // Views\planet_info.xaml line 1
                {
                    global::Windows.UI.Xaml.Controls.Page element1 = (global::Windows.UI.Xaml.Controls.Page)(target);
                    ((global::Windows.UI.Xaml.Controls.Page)element1).Loaded += this.Page_Loaded;
                }
                break;
            case 2: // Views\planet_info.xaml line 15
                {
                    this.InfoPopup = (global::Windows.UI.Xaml.Controls.Primitives.Popup)(target);
                }
                break;
            case 3: // Views\planet_info.xaml line 28
                {
                    this.InfoGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                    ((global::Windows.UI.Xaml.Controls.Grid)this.InfoGrid).SizeChanged += this.InfoGrid_SizeChanged;
                }
                break;
            case 4: // Views\planet_info.xaml line 33
                {
                    this.InfoHeaderGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                    ((global::Windows.UI.Xaml.Controls.Grid)this.InfoHeaderGrid).SizeChanged += this.InfoHeaderGrid_SizeChanged;
                }
                break;
            case 5: // Views\planet_info.xaml line 101
                {
                    this.InfoRefresh = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.InfoRefresh).Click += this.InfoRefresh_Click;
                }
                break;
            case 6: // Views\planet_info.xaml line 109
                {
                    this.InfoElementGrid = (global::Windows.UI.Xaml.Controls.GridView)(target);
                }
                break;
            case 7: // Views\planet_info.xaml line 194
                {
                    this.ESIButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.ESIButton).Click += this.ESIButton_Click;
                }
                break;
            case 8: // Views\planet_info.xaml line 195
                {
                    this.ESIText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 9: // Views\planet_info.xaml line 183
                {
                    this.DistanceButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.DistanceButton).Click += this.DistanceButton_Click;
                }
                break;
            case 10: // Views\planet_info.xaml line 184
                {
                    this.DistanceText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 11: // Views\planet_info.xaml line 172
                {
                    this.PeriodButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.PeriodButton).Click += this.PeriodButton_Click;
                }
                break;
            case 12: // Views\planet_info.xaml line 173
                {
                    this.PeriodText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 13: // Views\planet_info.xaml line 161
                {
                    this.TeqButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.TeqButton).Click += this.TeqButton_Click;
                }
                break;
            case 14: // Views\planet_info.xaml line 162
                {
                    this.TeqText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 15: // Views\planet_info.xaml line 150
                {
                    this.FluxButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.FluxButton).Click += this.FluxButton_Click;
                }
                break;
            case 16: // Views\planet_info.xaml line 151
                {
                    this.FluxText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 17: // Views\planet_info.xaml line 139
                {
                    this.RadiusButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.RadiusButton).Click += this.RadiusButton_Click;
                }
                break;
            case 18: // Views\planet_info.xaml line 140
                {
                    this.RadiusText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 19: // Views\planet_info.xaml line 128
                {
                    this.MassButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.MassButton).Click += this.MassButton_Click;
                }
                break;
            case 20: // Views\planet_info.xaml line 129
                {
                    this.MassText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 21: // Views\planet_info.xaml line 117
                {
                    this.TypeButton = (global::Windows.UI.Xaml.Controls.Button)(target);
                    ((global::Windows.UI.Xaml.Controls.Button)this.TypeButton).Click += this.TypeButton_Click;
                }
                break;
            case 22: // Views\planet_info.xaml line 118
                {
                    this.TypeText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 23: // Views\planet_info.xaml line 43
                {
                    this.ImgNameGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                }
                break;
            case 24: // Views\planet_info.xaml line 89
                {
                    this.HabitabilityPanel = (global::Windows.UI.Xaml.Controls.StackPanel)(target);
                }
                break;
            case 25: // Views\planet_info.xaml line 90
                {
                    this.PlanetHabitabilityImg = (global::Windows.UI.Xaml.Controls.Image)(target);
                }
                break;
            case 26: // Views\planet_info.xaml line 91
                {
                    this.PlanetHabitabilityText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 27: // Views\planet_info.xaml line 54
                {
                    this.PlanetNameText = (global::Windows.UI.Xaml.Controls.TextBlock)(target);
                }
                break;
            case 28: // Views\planet_info.xaml line 55
                {
                    this.FavoriteButton = (global::Windows.UI.Xaml.Controls.Primitives.ToggleButton)(target);
                    ((global::Windows.UI.Xaml.Controls.Primitives.ToggleButton)this.FavoriteButton).Unchecked += this.FavoriteButton_Unchecked;
                    ((global::Windows.UI.Xaml.Controls.Primitives.ToggleButton)this.FavoriteButton).Checked += this.FavoriteButton_Checked;
                }
                break;
            case 29: // Views\planet_info.xaml line 57
                {
                    this.FavoriteCheckedImage = (global::Windows.UI.Xaml.Controls.Image)(target);
                }
                break;
            case 30: // Views\planet_info.xaml line 71
                {
                    this.FavoriteUncheckedImage = (global::Windows.UI.Xaml.Controls.Image)(target);
                }
                break;
            case 31: // Views\planet_info.xaml line 51
                {
                    this.PlanetImageBrush = (global::Windows.UI.Xaml.Media.ImageBrush)(target);
                }
                break;
            case 32: // Views\planet_info.xaml line 16
                {
                    this.InfoPopupGrid = (global::Windows.UI.Xaml.Controls.Grid)(target);
                }
                break;
            case 33: // Views\planet_info.xaml line 17
                {
                    this.InfoPopupBorder = (global::Windows.UI.Xaml.Controls.Border)(target);
                }
                break;
            default:
                break;
            }
            this._contentLoaded = true;
        }

        /// <summary>
        /// GetBindingConnector(int connectionId, object target)
        /// </summary>
        [global::System.CodeDom.Compiler.GeneratedCodeAttribute("Microsoft.Windows.UI.Xaml.Build.Tasks"," 0.0.0.0")]
        [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public global::Windows.UI.Xaml.Markup.IComponentConnector GetBindingConnector(int connectionId, object target)
        {
            global::Windows.UI.Xaml.Markup.IComponentConnector returnValue = null;
            return returnValue;
        }
    }
}
