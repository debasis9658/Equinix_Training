#include <iostream>
#include <vector>
using namespace std;

int main()
{
    int n, m;
    cin>>n>>m;
    
    vector<int> first(n);
    vector<int> second(m);
    
    for(int i = 0; i < n; i++){
        cin>>first[i];
    }
    for(int i = 0; i < m; i++){
        cin>>second[i];
    }
    
    
    cout<<endl;
    vector<vector<int> > ans;
    
    for(int i = m - 1; i >= 0; i--){
        int a = second[i];
        vector<int> temp;
        
        int mult = 0;
        int carry = 0;
        for(int j = n - 1; j >= 0; j--){
            int b = first[j];
            if(a * b + carry >= 10){
                mult = (a * b + carry) % 10;
                carry = (a * b + carry) / 10;
                temp.insert(temp.begin(), mult);
            }
            else{
                temp.insert(temp.begin(), a * b + carry);
                carry = 0;
            }
            
        }
        if(carry > 0){
            temp.insert(temp.begin(), carry);
        }
        ans.push_back(temp);
    }
    cout<<endl;
    vector<int> finalAns;
    for(int i = 0; i < ans.size(); i++){
        for(int j = 0; j < ans[i].size(); j++){
            cout<<ans[i][j]<<" ";
        }
        cout<<endl;
    }
    return 0;
}